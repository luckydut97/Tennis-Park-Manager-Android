package com.luckydut97.tennispark_tablet.data.network.activity

import com.luckydut97.tennispark_tablet.data.network.event.ContentPage

data class ActivityResponse(
    val actId: Int?,          // ✅ 실제 서버에서 사용하는 ID 필드
    val beginAt: String?,
    val endAt: String?,
    val activeDays: List<String>?,
    val participantCount: Int?,
    val placeName: String?,
    val address: String?,
    val courtType: String?,
    val isRecurring: Boolean?,
    val courtName: String?
)

data class ActivityListResponse(
    val acts: ContentPage<ActivityResponse>
)

