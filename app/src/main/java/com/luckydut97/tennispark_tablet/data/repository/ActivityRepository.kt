package com.luckydut97.tennispark_tablet.data.repository

import com.luckydut97.tennispark_tablet.data.network.activity.*
import com.luckydut97.tennispark_tablet.data.model.Activity
import com.luckydut97.tennispark_tablet.utils.Constants
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log

class ActivityRepository(
    private val api: ActivityApiService
) {

    suspend fun registerActivity(activity: Activity): Result<Unit> {
        return try {
            val request = activity.toRegisterRequest()
            android.util.Log.d("디버깅 로그:", "registerActivity 호출 - URL: ${Constants.BASE_URL}api/admin/activities")
            android.util.Log.d("디버깅 로그:", "registerActivity Request: $request")
            
            val res = api.registerActivity("Bearer ${Constants.ACCESS_TOKEN}", request)
            android.util.Log.d("디버깅 로그:", "registerActivity Response: $res")
            
            if (res.success) {
                Result.success(Unit)
            } else {
                val message = res.error?.message ?: "등록에 실패했습니다"
                Result.failure(Exception(message))
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "요청한 API를 찾을 수 없습니다. (404)"
                401 -> "인증에 실패했습니다. (401)"
                403 -> "권한이 없습니다. (403)"
                500 -> "서버 오류가 발생했습니다. (500)"
                else -> "HTTP 오류: ${e.code()}"
            }
            android.util.Log.e("디버깅 로그:", "registerActivity HTTP Error: $errorMessage", e)
            Result.failure(Exception(errorMessage))
        } catch (e: SocketTimeoutException) {
            android.util.Log.e("디버깅 로그:", "registerActivity 타임아웃 에러", e)
            Result.failure(Exception("서버 응답 시간이 초과되었습니다. 네트워크 상태를 확인해주세요."))
        } catch (e: UnknownHostException) {
            android.util.Log.e("디버깅 로그:", "registerActivity 호스트를 찾을 수 없음", e)
            Result.failure(Exception("서버에 연결할 수 없습니다. 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            android.util.Log.e("디버깅 로그:", "registerActivity 에러", e)
            Result.failure(e)
        }
    }

    suspend fun getActivityList(): Result<List<Activity>> {
        return try {
            android.util.Log.d("디버깅 로그:", "getActivityList 호출 - URL: ${Constants.BASE_URL}api/admin/activities")

            val res = api.getActivities("Bearer ${Constants.ACCESS_TOKEN}")
            android.util.Log.d("디버깅 로그:", "getActivityList Response success: ${res.success}")

            val activities: List<Activity> =
                if (res.success && res.response != null && res.response.isJsonObject) {
                    val rootObj = res.response.asJsonObject
                    if (rootObj.has("acts")) {
                        val acts = rootObj.getAsJsonObject("acts")
                        if (acts.has("content")) {
                            val content = acts.getAsJsonArray("content")
                            android.util.Log.d("디버깅 로그:", "전체 API 응답 content: $content")
                            val responses = Gson().fromJson<List<ActivityResponse>>(
                                content,
                                object : TypeToken<List<ActivityResponse>>() {}.type
                            ) ?: emptyList()
                            android.util.Log.d(
                                "디버깅 로그:",
                                "파싱된 ActivityResponse 개수: ${responses.size}"
                            )
                            responses.map { it.toDomainModel() }
                        } else {
                            emptyList()
                        }
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            Result.success(activities)
        } catch (e: Exception) {
            android.util.Log.e("디버깅 로그:", "getActivityList 에러", e)
            Result.failure(e)
        }
    }

    suspend fun updateActivity(activity: Activity): Result<Unit> {
        return try {
            val request = activity.toUpdateRequest()
            val res = api.updateActivity("Bearer ${Constants.ACCESS_TOKEN}", activity.id, request)

            if (res.success) {
                Result.success(Unit)
            } else {
                val message = res.error?.message ?: "수정에 실패했습니다"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            android.util.Log.e("디버깅 로그:", "updateActivity 에러", e)
            Result.failure(e)
        }
    }

    suspend fun deleteActivity(activityId: String): Result<Unit> {
        return try {
            val res = api.deleteActivity("Bearer ${Constants.ACCESS_TOKEN}", activityId)

            if (res.success) {
                Result.success(Unit)
            } else {
                val message = res.error?.message ?: "삭제에 실패했습니다"
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            android.util.Log.e("디버깅 로그:", "deleteActivity에서 예외 발생", e)
            Result.failure(e)
        }
    }
}

// Extension functions for DTO <-> Domain Model conversion
private fun Activity.toRegisterRequest(): ActivityRegisterRequest {
    return ActivityRegisterRequest(
        beginAt = startTime,
        endAt = endTime,
        activeDays = selectedDays.map { dayToApiFormat(it) },
        participantCount = 12, // 기본값
        placeName = location,
        address = address,
        courtType = courtType, // Activity의 courtType 사용
        isRecurring = isRepeating,
        courtName = court
    )
}

private fun Activity.toUpdateRequest(): ActivityUpdateRequest {
    return ActivityUpdateRequest(
        beginAt = startTime,
        endAt = endTime,
        activeDays = selectedDays.map { dayToApiFormat(it) },
        participantCount = 12, // 기본값
        placeName = location,
        address = address,
        courtType = courtType, // Activity의 courtType 사용
        isRecurring = isRepeating,
        courtName = court
    )
}

private fun ActivityResponse.toDomainModel(): Activity {
    android.util.Log.d("디버깅 로그:", "변환 중인 ActivityResponse: actId=${actId}, placeName=${placeName}")

    // actId를 사용하거나 UUID 생성
    val actualId = actId?.toString() ?: java.util.UUID.randomUUID().toString().also {
        android.util.Log.w("디버깅 로그:", "actId가 null이므로 UUID 생성: $it")
    }

    android.util.Log.d("디버깅 로그:", "사용할 ID: $actualId")

    return try {
        Activity(
            id = actualId,
            location = placeName ?: "",
            court = courtName ?: "",
            startTime = beginAt?.take(5) ?: "00:00", // HH:mm 형식으로 변환
            endTime = endAt?.take(5) ?: "00:00",     // HH:mm 형식으로 변환
            selectedDays = activeDays?.map { apiDayToDisplay(it) } ?: emptyList(),
            address = address ?: "",
            isRepeating = isRecurring ?: false,
            courtType = courtType ?: "GAME" // 코트 타입 추가
        )
    } catch (e: Exception) {
        android.util.Log.e("디버깅 로그:", "ActivityResponse to Domain Model 변환 실패", e)
        throw e
    }
}

private fun dayToApiFormat(day: String): String {
    return when (day) {
        "일" -> "SUN"
        "월" -> "MON"
        "화" -> "TUE"
        "수" -> "WED"
        "목" -> "THU"
        "금" -> "FRI"
        "토" -> "SAT"
        else -> day
    }
}

private fun apiDayToDisplay(day: String): String {
    return when (day) {
        "SUN" -> "일"
        "MON" -> "월"
        "TUE" -> "화"
        "WED" -> "수"
        "THU" -> "목"
        "FRI" -> "금"
        "SAT" -> "토"
        else -> day
    }
}
