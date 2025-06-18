package com.luckydut97.tennispark_tablet.data.network

import com.google.gson.JsonElement

data class ApiResponse(
    val success: Boolean,
    val response: JsonElement? = null,
    val error: ApiError? = null
)

data class ApiError(
    val status: Int?,
    val message: String?
)
