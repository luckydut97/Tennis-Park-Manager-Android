package com.luckydut97.tennispark_tablet.data.network.admin

import retrofit2.http.GET
import retrofit2.http.Header
import com.luckydut97.tennispark_tablet.data.network.ApiResponse

interface AdminApiService {
    @GET("api/admin/member-activities/monthly")
    suspend fun getMonthlyMemberActivities(
        @Header("Authorization") authorization: String
    ): ApiResponse

    @GET("api/admin/member-activities/total")
    suspend fun getTotalMemberActivities(
        @Header("Authorization") authorization: String
    ): ApiResponse
}