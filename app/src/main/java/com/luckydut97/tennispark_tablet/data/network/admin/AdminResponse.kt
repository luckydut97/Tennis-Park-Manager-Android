package com.luckydut97.tennispark_tablet.data.network.admin

// 이번달 회원 활동 응답
data class MonthlyMemberActivitiesResponse(
    val participantCount: Int,      // 참여 인원 수
    val totalPoints: Int,           // 누적 포인트
    val topPointEarner: String,     // 최다 포인트 적립자
    val topPointSpender: String     // 최다 포인트 사용자
)

// 회원 통계 응답
data class TotalMemberActivitiesResponse(
    val totalMembers: Int,          // 총 회원 수
    val totalActivities: Int,       // 총 활동 횟수
    val topPlayer: String,          // 리그 1위 회원
    val topScore: Int               // 리그 1위 점수
)