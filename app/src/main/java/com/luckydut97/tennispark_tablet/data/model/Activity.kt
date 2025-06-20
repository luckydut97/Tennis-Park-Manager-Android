package com.luckydut97.tennispark_tablet.data.model

import java.util.UUID

/**
 * 테니스 활동을 나타내는 데이터 모델
 */
data class Activity(
    val id: String = UUID.randomUUID().toString(),
    val location: String,
    val court: String,
    val startTime: String,
    val endTime: String,
    val selectedDays: List<String>,
    val address: String,
    val isRepeating: Boolean,
    val courtType: String = "GAME", // 코트 타입 추가 (기본값: GAME)
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) {
    /**
     * 시간 범위를 문자열로 반환
     * @return "10:00 - 12:00" 형태의 문자열
     */
    val timeRange: String
        get() = "$startTime - $endTime"

    /**
     * 장소와 코트를 결합한 표시명
     * @return "수도권 테니스장 A코트" 형태의 문자열
     */
    val displayName: String
        get() = "$location $court"

    /**
     * 선택된 요일들을 문자열로 반환
     * @return "월, 수, 금" 형태의 문자열
     */
    val daysDisplay: String
        get() = selectedDays.joinToString(", ")

    /**
     * 활동이 유효한지 확인
     * @return 필수 필드가 모두 채워져 있고 활성 상태인지 여부
     */
    val isValid: Boolean
        get() = location.isNotBlank() &&
                court.isNotBlank() &&
                startTime.isNotBlank() &&
                endTime.isNotBlank() &&
                selectedDays.isNotEmpty() &&
                address.isNotBlank() &&
                isActive
}

/**
 * 활동 생성을 위한 빌더 클래스
 */
class ActivityBuilder {
    private var location: String = ""
    private var court: String = ""
    private var startTime: String = ""
    private var endTime: String = ""
    private var selectedDays: List<String> = emptyList()
    private var address: String = ""
    private var isRepeating: Boolean = false
    private var courtType: String = "GAME" // 코트 타입 추가 (기본값: GAME)

    fun location(location: String) = apply { this.location = location }
    fun court(court: String) = apply { this.court = court }
    fun startTime(startTime: String) = apply { this.startTime = startTime }
    fun endTime(endTime: String) = apply { this.endTime = endTime }
    fun selectedDays(days: List<String>) = apply { this.selectedDays = days }
    fun address(address: String) = apply { this.address = address }
    fun isRepeating(repeating: Boolean) = apply { this.isRepeating = repeating }
    fun courtType(type: String) = apply { this.courtType = type }

    fun build(): Activity {
        return Activity(
            location = location,
            court = court,
            startTime = startTime,
            endTime = endTime,
            selectedDays = selectedDays,
            address = address,
            isRepeating = isRepeating,
            courtType = courtType
        )
    }
}
