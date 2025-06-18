package com.luckydut97.tennispark_tablet.data.network

data class ActivityResponse(
    val id: String,
    val beginAt: String,
    val endAt: String,
    val activeDays: List<String>,
    val participantCount: Int,
    val placeName: String,
    val address: String,
    val courtType: String,
    val isRecurring: Boolean,
    val courtName: String
)
