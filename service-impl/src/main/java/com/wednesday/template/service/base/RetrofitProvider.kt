package com.wednesday.template.service.base

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wednesday.template.service.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

fun getRetrofit(context: Context, vararg interceptors: Interceptor): Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor()

    httpLoggingInterceptor.level = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        false -> HttpLoggingInterceptor.Level.NONE
    }

    val okHttpClient = OkHttpClient().newBuilder().run {

        interceptors.forEach {
            addInterceptor(it)
        }

        if (BuildConfig.DEBUG) {
            addInterceptor(
                ChuckerInterceptor
                    .Builder(context)
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }

        addInterceptor(httpLoggingInterceptor)

        build()
    }

    val apiBaseUrl = "https://www.metaweather.com/"
    val contentType = "application/json".toMediaType()
    val json = Json {
        ignoreUnknownKeys = true
    }

    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(apiBaseUrl)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}
