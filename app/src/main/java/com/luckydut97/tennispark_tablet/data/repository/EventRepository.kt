package com.luckydut97.tennispark_tablet.data.repository

import android.util.Log
import com.luckydut97.tennispark_tablet.data.network.event.EventApiService
import com.luckydut97.tennispark_tablet.data.network.event.EventRequest
import com.luckydut97.tennispark_tablet.data.network.event.EventItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.JsonElement
import com.luckydut97.tennispark_tablet.utils.Constants
import retrofit2.HttpException

class EventRepository(
    private val api: EventApiService
) {
    suspend fun getEventList(token: String = Constants.ACCESS_TOKEN, page: Int = 0, size: Int = 10): Result<List<EventItem>> {
        val authHeader = "Bearer $token"
        return try {
            Log.d("EVENT 디버깅:", "이벤트 목록 조회 요청 - Authorization: $authHeader, page: $page, size: $size")
            val res = api.getEvents(authHeader, page, size)
            Log.d("EVENT 디버깅:", "이벤트 목록 조회 응답: $res")
            if (res.success && res.response != null && res.response.isJsonObject) {
                val rootObj = res.response.asJsonObject
                if (rootObj.has("events")) {
                    val events = rootObj.getAsJsonObject("events")
                    if (events.has("content")) {
                        val content = events.getAsJsonArray("content")
                        val list = Gson().fromJson<List<EventItem>>(content, object: TypeToken<List<EventItem>>(){}.type)
                        Result.success(list ?: emptyList())
                    } else {
                        Result.success(emptyList())
                    }
                } else {
                    Result.success(emptyList())
                }
            } else {
                Log.e("EVENT 디버깅:", "이벤트 목록 조회 에러 응답: ${res.error}")
                Result.failure(Exception(res.error?.message ?: "이벤트 목록 조회 실패"))
            }
        } catch (e: Exception) {
            Log.e("EVENT 디버깅:", "이벤트 목록 조회 API 호출 예외", e)
            Result.failure(e)
        }
    }

    suspend fun createEvent(token: String = Constants.ACCESS_TOKEN, body: EventRequest): Result<Unit> {
        val authHeader = "Bearer $token"
        return try {
            Log.d("EVENT 디버깅:", "이벤트 등록 요청: $body, Authorization: $authHeader")
            val res = api.createEvent(authHeader, body)
            Log.d("EVENT 디버깅:", "이벤트 등록 응답: $res")
            if (res.success) {
                Result.success(Unit)
            } else {
                Log.e("EVENT 디버깅:", "이벤트 등록 에러: ${res.error}")
                val errorMessage = res.error?.message ?: "이벤트 등록 실패"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: HttpException) {
            Log.e("EVENT 디버깅:", "이벤트 등록 HTTP 에러 - 코드: ${e.code()}")
            try {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("EVENT 디버깅:", "이벤트 등록 HTTP 에러 응답 본문: $errorBody")

                // 에러 응답 파싱해서 구체적인 메시지 추출
                val errorMessage = if (errorBody != null) {
                    try {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            errorBody,
                            com.luckydut97.tennispark_tablet.data.network.ApiResponse::class.java
                        )
                        errorResponse.error?.message ?: "이벤트 등록 실패"
                    } catch (parseException: Exception) {
                        "이벤트 등록 실패 (${e.code()})"
                    }
                } else {
                    "이벤트 등록 실패 (${e.code()})"
                }

                Result.failure(Exception(errorMessage))
            } catch (ex: Exception) {
                Log.e("EVENT 디버깅:", "에러 응답 본문 읽기 실패", ex)
                Result.failure(Exception("이벤트 등록 실패 (${e.code()})"))
            }
        } catch (e: Exception) {
            Log.e("EVENT 디버깅:", "이벤트 등록 API 호출 예외", e)
            Result.failure(e)
        }
    }

    suspend fun updateEvent(token: String = Constants.ACCESS_TOKEN, eventId: Long, body: EventRequest): Result<Unit> {
        val authHeader = "Bearer $token"
        return try {
            Log.d("EVENT 디버깅:", "이벤트 수정 요청 - eventId: $eventId, body: $body, Authorization: $authHeader")
            val res = api.updateEvent(authHeader, eventId, body)
            Log.d("EVENT 디버깅:", "이벤트 수정 응답: $res")
            if (res.success) {
                Result.success(Unit)
            } else {
                Log.e("EVENT 디버깅:", "이벤트 수정 에러: ${res.error}")
                Result.failure(Exception(res.error?.message ?: "이벤트 수정 실패"))
            }
        } catch (e: Exception) {
            Log.e("EVENT 디버깅:", "이벤트 수정 API 호출 예외", e)
            Result.failure(e)
        }
    }

    suspend fun deleteEvent(token: String = Constants.ACCESS_TOKEN, eventId: Long): Result<Unit> {
        val authHeader = "Bearer $token"
        return try {
            Log.d("EVENT 디버깅:", "이벤트 삭제 요청 - eventId: $eventId, Authorization: $authHeader")
            val res = api.deleteEvent(authHeader, eventId)
            Log.d("EVENT 디버깅:", "이벤트 삭제 응답: $res")
            if (res.success) {
                Result.success(Unit)
            } else {
                Log.e("EVENT 디버깅:", "이벤트 삭제 에러: ${res.error}")
                Result.failure(Exception(res.error?.message ?: "이벤트 삭제 실패"))
            }
        } catch (e: Exception) {
            Log.e("EVENT 디버깅:", "이벤트 삭제 API 호출 예외", e)
            Result.failure(e)
        }
    }
}
