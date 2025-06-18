package com.luckydut97.tennispark_tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckydut97.tennispark_tablet.data.network.ActivityRegisterRequest
import com.luckydut97.tennispark_tablet.data.network.ActivityResponse
import com.luckydut97.tennispark_tablet.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {
    private val _activityList = MutableStateFlow<List<ActivityResponse>>(emptyList())
    val activityList: StateFlow<List<ActivityResponse>> get() = _activityList

    fun refreshActivities() {
        viewModelScope.launch {
            android.util.Log.d("디버깅 로그:VM", "refreshActivities 호출")
            repository.getActivityList()
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "활동 목록 불러오기 성공: 건수=${it.size}")
                    _activityList.value = it
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "활동 목록 불러오기 실패: ${it.message}")
                }
        }
    }

    suspend fun registerActivity(request: ActivityRegisterRequest): Boolean {
        android.util.Log.d("디버깅 로그:VM", "registerActivity() 호출: $request")
        return try {
            repository.registerActivity(request)
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "등록 성공! 활동 새로고침")
                    return true
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "등록 실패: ${it.message}")
                    return false
                }
            false
        } catch (e: Exception) {
            android.util.Log.d("디버깅 로그:VM", "등록 중 예외 발생: ${e.message}")
            false
        }
    }
}
