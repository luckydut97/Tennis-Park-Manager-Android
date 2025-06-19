package com.luckydut97.tennispark_tablet.data.network.event

data class EventRequest(
    val title: String,
    val content: String,
    val point: Int
)

data class EventItem(
    val id: Long,
    val title: String,
    val content: String,
    val point: Int,
    val imageUrl: String?
)
