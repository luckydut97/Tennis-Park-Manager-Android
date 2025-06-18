package com.luckydut97.tennispark_tablet.data.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET

// 현실적으로 ApiResponse<Nothing> 등으로 받지만, 일단 Any로 받아도 무방
interface ActivityApiService {
    @POST("api/admin/activities")
    suspend fun registerActivity(
        @Header("Authorization") authorization: String,
        @Body body: ActivityRegisterRequest
    ): ApiResponse

    @GET("api/admin/activities")
    suspend fun getActivities(
        @Header("Authorization") authorization: String
    ): ApiResponse
}
