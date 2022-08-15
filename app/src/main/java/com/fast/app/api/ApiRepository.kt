package com.fast.app.api

import com.fast.app.BuildConfig
import com.fast.app.entity.ArticleBean
import com.fast.app.entity.Page
import com.fast.app.ui.api.ApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

object ApiRepository{
    private val okhttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val apiService by lazy {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }

    suspend fun searchArticlesList(key: String) = apiService.searchArticlesList(key)
}

interface ApiService {
    @FormUrlEncoded
    @POST("article/query/0/json")
    suspend fun searchArticlesList(@Field("k") key: String?): ApiResponse<Page<List<ArticleBean>>>
}