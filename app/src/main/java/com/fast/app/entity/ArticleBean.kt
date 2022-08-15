package com.fast.app.entity


data class ArticleBean(
    val apkLink: String? = null,
    val author: String? = null,
    val chapterId: Int = 0,
    val chapterName: String? = null,
    val collect: Boolean = false,
    val courseId: Int = 0,
    val desc: String? = null,
    val envelopePic: String? = null,
    val isFresh: Boolean = false,
    val id: Int = 0,
    val link: String? = null,
    val niceDate: String? = null,
    val origin: String? = null,
    val projectLink: String? = null,
    val publishTime: Long = 0,
    val superChapterId: Int = 0,
    val superChapterName: String? = null,
    val title: String? = null,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    val tags: List<*>? = null
)

data class Page<T>(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: T
)