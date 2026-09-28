package com.example.astra.data.remote

import com.example.astra.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object HindsightClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val apiKey = BuildConfig.HINDSIGHT_API_KEY
        val requestBuilder = original.newBuilder()
            .header("Content-Type", "application/json")
        if (apiKey.isNotBlank()) {
            requestBuilder.header("Authorization", "Bearer $apiKey")
            requestBuilder.header("X-API-Key", apiKey)
        }
        chain.proceed(requestBuilder.build())
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val apiService: HindsightApiService by lazy {
        val baseUrl = if (BuildConfig.HINDSIGHT_BASE_URL.isNotBlank()) {
            if (BuildConfig.HINDSIGHT_BASE_URL.endsWith("/")) BuildConfig.HINDSIGHT_BASE_URL
            else "${BuildConfig.HINDSIGHT_BASE_URL}/"
        } else {
            "https://api.hindsight.vectorize.io/"
        }

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HindsightApiService::class.java)
    }
}
