package com.cpzero.lib_base.utils

import android.util.Log
import org.jetbrains.annotations.NonNls
import java.util.*
import java.util.Collections.unmodifiableList

class Timber private constructor() {


    init {
        throw AssertionError()
    }

    abstract class Tree {

        val defTag = "CPZero"

        @get:JvmSynthetic
        internal val explicitTag = hashMapOf<String, String>()

        @get:JvmSynthetic // Hide from public API.
        internal open val tag: String?
            get() {
                return explicitTag["tag"]
            }

        open fun v(message: String?, tag: String? = this.tag) {
            prepareLog(Log.VERBOSE, tag, message)
        }

        open fun d(message: String?, tag: String? = this.tag) {
            prepareLog(Log.DEBUG, tag, message)
        }

        open fun i(message: String?, tag: String? = this.tag) {
            prepareLog(Log.INFO, tag, message)
        }

        open fun w(message: String?, tag: String? = this.tag) {
            prepareLog(Log.WARN, tag, message)
        }

        open fun e(message: String?, tag: String? = this.tag) {
            prepareLog(Log.ERROR, tag, message)
        }

        open fun wtf(message: String?, tag: String? = this.tag) {
            prepareLog(Log.ASSERT, tag, message)
        }

        open fun l(priority: Int, message: String?, tag: String? = this.tag) {
            prepareLog(priority, tag, message)
        }

        private fun prepareLog(priority: Int, tag: String?, message: String?) {
            if (message.isNullOrEmpty()) {
                return
            }
            log(priority, tag ?: this.tag, message)
        }


        /**
         * Write a log message to its destination. Called for all level-specific methods by default.
         *
         * @param priority Log level. See [Log] for constants.
         * @param tag Explicit or inferred tag. May be `null`.
         * @param message Formatted log message.
         */
        protected abstract fun log(priority: Int, tag: String?, message: String)
    }

    /** A [Tree] for debug builds. Automatically infers the tag from the calling class. */
    open class DebugTree : Tree() {

        override val tag: String?
            get() = super.tag ?: defTag

        /**
         * Break up `message` into maximum-length chunks (if needed) and send to either
         * [Log.println()][Log.println] or
         * [Log.wtf()][Log.wtf] for logging.
         * {@inheritDoc}
         */
        override fun log(priority: Int, tag: String?, message: String) {
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                } else {
                    Log.println(priority, tag, message)
                }
                return
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = newline.coerceAtMost(i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newline)
                i++
            }
        }

        companion object {
            private const val MAX_LOG_LENGTH = 4000
        }
    }

    companion object Forest : Tree() {

        @JvmStatic
        override fun v(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.v(message, tag) }
        }

        @JvmStatic
        override fun d(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.d(message, tag) }
        }

        @JvmStatic
        override fun i(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.i(message, tag) }
        }

        @JvmStatic
        override fun w(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.w(message, tag) }
        }

        @JvmStatic
        override fun e(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.e(message, tag) }
        }

        @JvmStatic
        override fun wtf(@NonNls message: String?, tag: String?) {
            treeArray.forEach { it.wtf(message, tag) }
        }

        @JvmStatic
        override fun l(priority: Int, @NonNls message: String?, tag: String?) {
            treeArray.forEach { it.l(priority, message, tag) }
        }

        override fun log(priority: Int, tag: String?, message: String) {
            throw AssertionError() // Missing override for log method.
        }

        /**
         * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
         * instance rather than using static methods or to facilitate testing.
         */
        @Suppress(
            "NOTHING_TO_INLINE", // Kotlin users should reference `Tree.Forest` directly.
            "NON_FINAL_MEMBER_IN_OBJECT" // For japicmp check.
        )

        @JvmStatic
        open inline fun asTree(): Tree = this

        /** Set a one-time tag for use on the next logging call. */
        @JvmStatic
        fun tag(tag: String): Tree {
            for (tree in treeArray) {
                tree.explicitTag["tag"] = tag
            }
            return this
        }

        /** Add a new logging tree. */
        @JvmStatic
        fun plant(tree: Tree) {
            require(tree !== this) { "Cannot plant Timber into itself." }
            synchronized(trees) {
                trees.add(tree)
                treeArray = trees.toTypedArray()
            }
        }

        /** Adds new logging trees. */
        @JvmStatic
        fun plant(vararg trees: Tree) {
            for (tree in trees) {
                require(tree !== this) { "Cannot plant Timber into itself." }
            }
            synchronized(Forest.trees) {
                Collections.addAll(Forest.trees, *trees)
                treeArray = Forest.trees.toTypedArray()
            }
        }

        /** Remove a planted tree. */
        @JvmStatic
        fun uproot(tree: Tree) {
            synchronized(trees) {
                require(trees.remove(tree)) { "Cannot uproot tree which is not planted: $tree" }
                treeArray = trees.toTypedArray()
            }
        }

        /** Remove all planted trees. */
        @JvmStatic
        fun uprootAll() {
            synchronized(trees) {
                trees.clear()
                treeArray = emptyArray()
            }
        }

        /** Return a copy of all planted [trees][Tree]. */
        @JvmStatic
        fun forest(): List<Tree> {
            synchronized(trees) {
                return unmodifiableList(trees.toList())
            }
        }

        @get:[JvmStatic JvmName("treeCount")]
        val treeCount
            get() = treeArray.size

        // Both fields guarded by 'trees'.
        private val trees = ArrayList<Tree>()

        @Volatile
        private var treeArray = emptyArray<Tree>()
    }
}