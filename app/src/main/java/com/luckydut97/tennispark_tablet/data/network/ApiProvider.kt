package com.luckydut97.tennispark_tablet.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.luckydut97.tennispark_tablet.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import com.luckydut97.tennispark_tablet.data.network.activity.ActivityApiService

object ApiProvider {
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        android.util.Log.d("API Call", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃
        .addInterceptor(loggingInterceptor)
        .build()

    init {
        android.util.Log.d("디버깅 로그:", "ApiProvider BASE_URL = ${Constants.BASE_URL}")
    }

    val activityService: ActivityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL) // "https://tennis-park.store/"
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivityApiService::class.java)
    }

    val eventService: com.luckydut97.tennispark_tablet.data.network.event.EventApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.luckydut97.tennispark_tablet.data.network.event.EventApiService::class.java)
    }
}
