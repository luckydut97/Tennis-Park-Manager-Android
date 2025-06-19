package com.luckydut97.tennispark_tablet.data.model

import java.util.UUID

/**
 * 테니스 이벤트를 나타내는 데이터 모델
 */
data class Event(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val points: Int,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val startDate: Long? = null,
    val endDate: Long? = null
) {
    /**
     * 이벤트가 유효한지 확인
     */
    val isValid: Boolean
        get() = title.isNotBlank() &&
                content.isNotBlank() &&
                points > 0 &&
                isActive

    /**
     * 이벤트가 현재 진행 중인지 확인
     */
    val isOngoing: Boolean
        get() {
            val now = System.currentTimeMillis()
            return when {
                startDate == null && endDate == null -> isActive
                startDate != null && endDate == null -> now >= startDate && isActive
                startDate == null && endDate != null -> now <= endDate && isActive
                else -> now >= (startDate ?: 0) && now <= (endDate ?: Long.MAX_VALUE) && isActive
            }
        }

    /**
     * 포인트를 문자열로 표시
     */
    val pointsDisplay: String
        get() = "${points} Point"
}

/**
 * 광고 배너를 나타내는 데이터 모델
 */
data class Banner(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val size: String,
    val imageUrl: String = "",
    val isUploaded: Boolean = false,
    val position: BannerPosition,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * 배너가 유효한지 확인
     */
    val isValid: Boolean
        get() = title.isNotBlank() && size.isNotBlank()

    /**
     * 배너 표시명
     */
    val displayName: String
        get() = "$title $size"
}

/**
 * 배너 위치를 나타내는 열거형
 */
enum class BannerPosition(val displayName: String) {
    MAIN_SCREEN("메인화면"),
    ACTIVITY_MARKET("활동시장"),
    PURCHASE_LIST("구매목록"),
    USER_INFO("회원정보");

    companion object {
        fun fromDisplayName(displayName: String): BannerPosition? {
            return values().find { it.displayName == displayName }
        }
    }
}

/**
 * 이벤트 타입을 나타내는 열거형
 */
enum class EventType(val displayName: String) {
    CHECK_IN("출석 체크"),
    ACTIVITY_CREATE("활동 만들기"),
    TOURNAMENT("토너먼트"),
    SPECIAL("특별 이벤트"),
    SEASONAL("시즌 이벤트");

    companion object {
        fun fromDisplayName(displayName: String): EventType? {
            return values().find { it.displayName == displayName }
        }
    }
}
