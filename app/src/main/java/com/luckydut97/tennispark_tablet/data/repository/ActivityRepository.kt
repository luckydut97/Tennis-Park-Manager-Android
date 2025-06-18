package com.luckydut97.tennispark_tablet.data.repository

import com.luckydut97.tennispark_tablet.data.network.*
import com.luckydut97.tennispark_tablet.utils.Constants
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.JsonElement
import android.util.Log

class ActivityRepository(
    private val api: ActivityApiService
) {

    suspend fun registerActivity(request: ActivityRegisterRequest): Result<Unit> {
        return try {
            android.util.Log.d("디버깅 로그:", "registerActivity 호출 - URL: ${Constants.BASE_URL}api/admin/activities")
            android.util.Log.d("디버깅 로그:", "registerActivity BASE_URL: ${Constants.BASE_URL}")
            android.util.Log.d("디버깅 로그:", "registerActivity Request: $request")
            android.util.Log.d("디버깅 로그:", "registerActivity Authorization: Bearer ${Constants.ACCESS_TOKEN}")
            
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

    suspend fun getActivityList(): Result<List<ActivityResponse>> {
        return try {
            android.util.Log.d("디버깅 로그:", "getActivityList 호출 - URL: ${Constants.BASE_URL}api/admin/activities")
            android.util.Log.d("디버깅 로그:", "getActivityList BASE_URL: ${Constants.BASE_URL}")
            android.util.Log.d("디버깅 로그:", "getActivityList Authorization: Bearer ${Constants.ACCESS_TOKEN}")

            val res = api.getActivities("Bearer ${Constants.ACCESS_TOKEN}")
            android.util.Log.d("디버깅 로그:", "getActivityList Response success: ${res.success}")
            android.util.Log.d("디버깅 로그:", "getActivityList Response data: ${res.response}")

            val list: List<ActivityResponse> = if (res.success && res.response != null && res.response.isJsonObject) {
                val rootObj = res.response.asJsonObject
                if (rootObj.has("acts")) {
                    val acts = rootObj.getAsJsonObject("acts")
                    if (acts.has("content")) {
                        val content = acts.getAsJsonArray("content")
                        Gson().fromJson(content, object : TypeToken<List<ActivityResponse>>() {}.type) ?: emptyList()
                    } else {
                        emptyList()
                    }
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
            Result.success(list)
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "요청한 API를 찾을 수 없습니다. (404)"
                401 -> "인증에 실패했습니다. (401)"
                403 -> "권한이 없습니다. (403)"
                500 -> "서버 오류가 발생했습니다. (500)"
                else -> "HTTP 오류: ${e.code()}"
            }
            android.util.Log.e("디버깅 로그:", "getActivityList HTTP Error: $errorMessage", e)
            Result.failure(Exception(errorMessage))
        } catch (e: SocketTimeoutException) {
            android.util.Log.e("디버깅 로그:", "getActivityList 타임아웃 에러", e)
            Result.failure(Exception("서버 응답 시간이 초과되었습니다. 네트워크 상태를 확인해주세요."))
        } catch (e: UnknownHostException) {
            android.util.Log.e("디버깅 로그:", "getActivityList 호스트를 찾을 수 없음", e)
            Result.failure(Exception("서버에 연결할 수 없습니다. 인터넷 연결을 확인해주세요."))
        } catch (e: Exception) {
            android.util.Log.e("디버깅 로그:", "getActivityList 에러", e)
            Result.failure(e)
        }
    }
}
