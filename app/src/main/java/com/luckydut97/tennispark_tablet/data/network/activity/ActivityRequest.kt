package com.luckydut97.tennispark_tablet.data.network.activity

data class ActivityRegisterRequest(
    val beginAt: String,            // "HH:mm"
    val endAt: String,              // "HH:mm"
    val activeDays: List<String>,   // 예: ["MON", "TUE"]
    val participantCount: Int,
    val placeName: String,
    val address: String,
    val courtType: String,          // "GAME", "CHALLENGE", "RALLY", "STUDY", "BEGINNER"
    val isRecurring: Boolean,       // 일정 반복 여부
    val courtName: String           // "A코트", "B코트" ...
)

data class ActivityUpdateRequest(
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