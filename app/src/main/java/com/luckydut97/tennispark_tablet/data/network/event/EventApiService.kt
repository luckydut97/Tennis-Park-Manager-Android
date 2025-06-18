package com.luckydut97.tennispark_tablet.data.network.event

import retrofit2.http.*
import com.luckydut97.tennispark_tablet.data.network.ApiResponse

interface EventApiService {
    @GET("api/admin/events")
    suspend fun getEvents(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): ApiResponse

    @POST("api/admin/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Body event: EventRequest
    ): ApiResponse

    @PUT("api/admin/events/{eventId}")
    suspend fun updateEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long,
        @Body event: EventRequest
    ): ApiResponse

    @DELETE("api/admin/events/{eventId}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: Long
    ): ApiResponse
}
