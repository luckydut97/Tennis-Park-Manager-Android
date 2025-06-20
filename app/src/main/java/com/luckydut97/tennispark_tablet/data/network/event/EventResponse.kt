package com.luckydut97.tennispark_tablet.data.network.event

data class EventItem(
    val id: Long,
    val title: String,
    val content: String,
    val point: Int,
    val imageUrl: String?
)

data class ContentPage<T>(
    val content: List<T>,
    val pageable: Any? = null, // 필요한 필드만 추후 추가 (pageNumber 등)
    val totalElements: Int = 0,
    val totalPages: Int = 1,
    val size: Int = 0,
    val number: Int = 0
)

data class EventListResponse(
    val events: ContentPage<EventItem>
)
