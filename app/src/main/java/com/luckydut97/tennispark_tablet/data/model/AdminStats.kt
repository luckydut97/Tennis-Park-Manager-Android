package com.luckydut97.tennispark_tablet.data.model

/**
 * 이번달 회원 활동 통계
 */
data class MonthlyMemberActivities(
    val participantCount: Int,      // 참여 인원 수
    val totalPoints: Int,           // 누적 포인트
    val topPointEarner: String,     // 최다 포인트 적립자
    val topPointSpender: String     // 최다 포인트 사용자
) {
    val participantCountDisplay: String
        get() = "${participantCount}명"

    val totalPointsDisplay: String
        get() = "${totalPoints}P"
}

/**
 * 전체 회원 통계
 */
data class TotalMemberActivities(
    val totalMembers: Int,          // 총 회원 수
    val totalActivities: Int,       // 총 활동 횟수
    val topPlayer: String,          // 리그 1위 회원
    val topScore: Int               // 리그 1위 점수
) {
    val totalMembersDisplay: String
        get() = "${totalMembers}명"

    val totalActivitiesDisplay: String
        get() = "${totalActivities}회"

    val topScoreDisplay: String
        get() = "${topScore}점"
}