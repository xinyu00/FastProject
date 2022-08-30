package com.fast.main.ui.activity

import android.view.View
import com.fast.common.base.XBaseActivity
import com.fast.common.utils.ToastUtils
import com.fast.common.utils.logd
import com.fast.common.utils.logi
import com.fast.common.vm.BaseViewModel
import com.fast.main.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class MainActivity : XBaseActivity<ActivityMainBinding,BaseViewModel>() {
    val tag = "ces"
    override fun initViews() {
//        mViewBinding.tvMain.setOnClickListener {
//            Toast.makeText(this,"弹框",Toast.LENGTH_SHORT).show()
//        }
        mViewBinding.tvMain.setOnClickListener {
            test1()
        }

        "12345".apply {
            "123456".apply {
                ToastUtils.showToast( this@apply)
            }
        }

    }

    fun test1(){
        thread {
            val startTime1 = System.nanoTime()
            for (i in 0..20){
                letMethodTest()
            }
            val durationTime = System.nanoTime()-startTime1
            "let耗时：$durationTime 毫秒".logd(tag)
        }
        thread {
            val startTime2 = System.nanoTime()
            for (i in 0..20){
                alsoMethodTest()
            }
            val durationTime = System.nanoTime()-startTime2
            "also耗时：$durationTime 毫秒".logd(tag)
        }
        thread {
            val startTime2 = System.nanoTime()
            for (i in 0..20){
                runMethodTest()
            }
            val durationTime = System.nanoTime()-startTime2
            "run耗时：$durationTime 毫秒".logd(tag)
        }
    }

    fun letMethodTest(){
        val original = "abc"
        original.let {
            ("The original String is $it").logd(this@MainActivity.javaClass.name) // "abc"
            it.reversed()
        }.let {
            ("The reverse String is $it").logd(this@MainActivity.javaClass.name) // "cba"
            it.length
        }.let {
            ("The length of the String is $it").logd(this@MainActivity.javaClass.name) // 3

        }
    }
    fun alsoMethodTest(){
        val original = "abc"

        original.also {
            ("The original String is $it").logd(this@MainActivity.javaClass.name) // "abc"
            it.reversed()
        }.also {
            ("The reverse String is ${it}") .logd(this@MainActivity.javaClass.name) // "abc"
            it.length
        }.also {
            ("The length of the String is ${it}").logd(this@MainActivity.javaClass.name)  // "abc"
        }
    }
    fun runMethodTest(){
        val original = "abc"
        original.run {
            ("The original String is $this").logd(this@MainActivity.javaClass.name) // "abc"
            reversed()
        }.run {
            ("The reverse String is $this") .logd(this@MainActivity.javaClass.name) // "abc"
            length
        }.run {
            ("The length of the String is $$this").logd(this@MainActivity.javaClass.name)  // "abc"
        }
    }

    fun samp1(){
        val flow = flow<String>{
            for (i in 1..2) {
                delay(500)
                appendMessage("flow: $i, ${currentCoroutineContext()[CoroutineName]?.name}")
                emit("$i")
            }
        }

        CoroutineScope(Dispatchers.Default + CoroutineName("c2")).launch {
            // flowOn() - 用于指定 flow 阶段的运行协程，如果不指定的话，默认 flow 阶段的运行协程和 collect 阶段的运行协程是一样的
            flow.flowOn(Dispatchers.Default + CoroutineName("c1"))
                .collect { value ->
                    appendMessage("collect $value, ${currentCoroutineContext()[CoroutineName]?.name}")
                }
        }
    }
    fun appendMessage(message: String) {
        val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH)
        val time = dateFormat.format(Date());
        val threadName = Thread.currentThread().name

        CoroutineScope(Dispatchers.Main).launch{
            val log = "$time: $message（$threadName）"
            mViewBinding.tvMain.append(log);
            mViewBinding.tvMain.append("\n");
            mViewBinding.tvMain.visibility = View.GONE
            log.logd("coroutine")
        }
    }
}