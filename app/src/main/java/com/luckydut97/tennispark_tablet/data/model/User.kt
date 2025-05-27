package com.luckydut97.tennispark_tablet.data.model

import java.util.UUID

/**
 * 테니스파크 사용자를 나타내는 데이터 모델
 */
data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val points: Int = 0,
    val level: UserLevel = UserLevel.BEGINNER,
    val profileImageUrl: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val lastCheckInDate: String = "",
    val totalCheckIns: Int = 0,
    val totalActivities: Int = 0
) {
    /**
     * 사용자가 유효한지 확인
     */
    val isValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() &&
                phoneNumber.isNotBlank() &&
                isActive

    /**
     * 사용자 표시명 (이름이 없으면 이메일의 앞부분 사용)
     */
    val displayName: String
        get() = when {
            name.isNotBlank() -> name
            email.isNotBlank() -> email.substringBefore("@")
            else -> "사용자"
        }

    /**
     * 포인트를 문자열로 표시
     */
    val pointsDisplay: String
        get() = "${points} Point"

    /**
     * 레벨과 포인트를 함께 표시
     */
    val levelWithPoints: String
        get() = "${level.displayName} (${pointsDisplay})"
}

/**
 * 사용자 레벨을 나타내는 열거형
 */
enum class UserLevel(
    val displayName: String,
    val minPoints: Int,
    val maxPoints: Int?
) {
    BEGINNER("초급자", 0, 499),
    INTERMEDIATE("중급자", 500, 1499),
    ADVANCED("상급자", 1500, 2999),
    EXPERT("전문가", 3000, 4999),
    MASTER("마스터", 5000, null);

    companion object {
        fun fromPoints(points: Int): UserLevel {
            return values().find { level ->
                points >= level.minPoints && (level.maxPoints == null || points <= level.maxPoints)
            } ?: BEGINNER
        }

        fun fromDisplayName(displayName: String): UserLevel? {
            return values().find { it.displayName == displayName }
        }
    }
}

/**
 * 출석 체크 정보를 나타내는 데이터 모델
 */
data class CheckIn(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val location: String,
    val court: String,
    val checkInTime: Long = System.currentTimeMillis(),
    val pointsEarned: Int,
    val qrCode: String = "",
    val isValid: Boolean = true
) {
    /**
     * 체크인 날짜를 문자열로 반환 (yyyy-MM-dd)
     */
    val checkInDate: String
        get() {
            val date = java.util.Date(checkInTime)
            val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            return formatter.format(date)
        }

    /**
     * 체크인 시간을 문자열로 반환 (HH:mm)
     */
    val checkInTimeDisplay: String
        get() {
            val date = java.util.Date(checkInTime)
            val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            return formatter.format(date)
        }

    /**
     * 장소와 코트 정보
     */
    val locationDisplay: String
        get() = "$location $court"
}

/**
 * 사용자 통계를 나타내는 데이터 모델
 */
data class UserStats(
    val userId: String,
    val totalPoints: Int = 0,
    val totalCheckIns: Int = 0,
    val totalActivities: Int = 0,
    val totalEvents: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val favoriteLocation: String = "",
    val favoriteCourt: String = "",
    val lastActivityDate: String = ""
) {
    /**
     * 평균 일일 포인트 (지난 30일 기준)
     */
    val averageDailyPoints: Double
        get() = if (totalCheckIns > 0) totalPoints.toDouble() / totalCheckIns else 0.0

    /**
     * 선호 장소 표시
     */
    val favoriteLocationDisplay: String
        get() = if (favoriteLocation.isNotBlank() && favoriteCourt.isNotBlank()) {
            "$favoriteLocation $favoriteCourt"
        } else {
            "정보 없음"
        }
}