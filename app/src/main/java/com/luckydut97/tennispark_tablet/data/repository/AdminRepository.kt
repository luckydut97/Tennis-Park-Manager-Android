package com.luckydut97.tennispark_tablet.data.repository

import android.util.Log
import com.luckydut97.tennispark_tablet.data.network.admin.AdminApiService
import com.luckydut97.tennispark_tablet.data.network.admin.MonthlyMemberActivitiesResponse
import com.luckydut97.tennispark_tablet.data.network.admin.TotalMemberActivitiesResponse
import com.luckydut97.tennispark_tablet.data.model.MonthlyMemberActivities
import com.luckydut97.tennispark_tablet.data.model.TotalMemberActivities
import com.luckydut97.tennispark_tablet.utils.Constants
import com.google.gson.Gson

class AdminRepository(
    private val api: AdminApiService
) {

    suspend fun getMonthlyMemberActivities(): Result<MonthlyMemberActivities> {
        return try {
            Log.d("ADMIN 디버깅:", "이번달 회원 활동 조회 요청")

            val res = api.getMonthlyMemberActivities("Bearer ${Constants.ACCESS_TOKEN}")
            Log.d("ADMIN 디버깅:", "이번달 회원 활동 조회 응답: $res")

            if (res.success && res.response != null && res.response.isJsonObject) {
                val responseObj = res.response.asJsonObject
                val monthlyStats = Gson().fromJson(
                    responseObj,
                    MonthlyMemberActivitiesResponse::class.java
                )

                val domainModel = monthlyStats.toDomainModel()
                Log.d("ADMIN 디버깅:", "이번달 회원 활동 변환 완료: $domainModel")

                Result.success(domainModel)
            } else {
                Log.e("ADMIN 디버깅:", "이번달 회원 활동 조회 에러: ${res.error}")
                Result.failure(Exception(res.error?.message ?: "이번달 회원 활동 조회 실패"))
            }
        } catch (e: Exception) {
            Log.e("ADMIN 디버깅:", "이번달 회원 활동 조회 예외", e)
            Result.failure(e)
        }
    }

    suspend fun getTotalMemberActivities(): Result<TotalMemberActivities> {
        return try {
            Log.d("ADMIN 디버깅:", "회원 통계 조회 요청")

            val res = api.getTotalMemberActivities("Bearer ${Constants.ACCESS_TOKEN}")
            Log.d("ADMIN 디버깅:", "회원 통계 조회 응답: $res")

            if (res.success && res.response != null && res.response.isJsonObject) {
                val responseObj = res.response.asJsonObject
                val totalStats = Gson().fromJson(
                    responseObj,
                    TotalMemberActivitiesResponse::class.java
                )

                val domainModel = totalStats.toDomainModel()
                Log.d("ADMIN 디버깅:", "회원 통계 변환 완료: $domainModel")

                Result.success(domainModel)
            } else {
                Log.e("ADMIN 디버깅:", "회원 통계 조회 에러: ${res.error}")
                Result.failure(Exception(res.error?.message ?: "회원 통계 조회 실패"))
            }
        } catch (e: Exception) {
            Log.e("ADMIN 디버깅:", "회원 통계 조회 예외", e)
            Result.failure(e)
        }
    }
}

// Extension functions for DTO -> Domain Model conversion
private fun MonthlyMemberActivitiesResponse.toDomainModel(): MonthlyMemberActivities {
    return MonthlyMemberActivities(
        participantCount = participantCount,
        totalPoints = totalPoints,
        topPointEarner = topPointEarner,
        topPointSpender = topPointSpender
    )
}

private fun TotalMemberActivitiesResponse.toDomainModel(): TotalMemberActivities {
    return TotalMemberActivities(
        totalMembers = totalMembers,
        totalActivities = totalActivities,
        topPlayer = topPlayer,
        topScore = topScore
    )
}