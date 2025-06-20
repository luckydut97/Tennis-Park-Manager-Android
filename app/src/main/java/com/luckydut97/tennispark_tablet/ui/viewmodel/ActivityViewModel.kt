package com.luckydut97.tennispark_tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckydut97.tennispark_tablet.data.model.Activity
import com.luckydut97.tennispark_tablet.data.network.activity.ActivityRegisterRequest
import com.luckydut97.tennispark_tablet.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {
    private val _activityList = MutableStateFlow<List<Activity>>(emptyList())
    val activityList: StateFlow<List<Activity>> get() = _activityList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun refreshActivities() {
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("디버깅 로그:VM", "refreshActivities 호출")
            repository.getActivityList()
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "활동 목록 불러오기 성공: 건수=${it.size}")
                    _activityList.value = it
                    _errorMessage.value = null
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "활동 목록 불러오기 실패: ${it.message}")
                    _errorMessage.value = it.message
                }
            _isLoading.value = false
        }
    }

    suspend fun registerActivity(activity: Activity): Boolean {
        android.util.Log.d("디버깅 로그:VM", "registerActivity() 호출: $activity")
        return try {
            _isLoading.value = true
            repository.registerActivity(activity)
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "등록 성공! 활동 새로고침")
                    refreshActivities()
                    return true
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "등록 실패: ${it.message}")
                    _errorMessage.value = it.message
                    return false
                }
            false
        } catch (e: Exception) {
            android.util.Log.d("디버깅 로그:VM", "등록 중 예외 발생: ${e.message}")
            _errorMessage.value = e.message
            false
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun updateActivity(activity: Activity): Boolean {
        android.util.Log.d("디버깅 로그:VM", "updateActivity() 호출: $activity")
        return try {
            _isLoading.value = true
            repository.updateActivity(activity)
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "수정 성공! 활동 새로고침")
                    refreshActivities()
                    return true
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "수정 실패: ${it.message}")
                    _errorMessage.value = it.message
                    return false
                }
            false
        } catch (e: Exception) {
            android.util.Log.d("디버깅 로그:VM", "수정 중 예외 발생: ${e.message}")
            _errorMessage.value = e.message
            false
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun deleteActivity(activityId: String): Boolean {
        android.util.Log.d("디버깅 로그:VM", "deleteActivity() 호출: $activityId")
        return try {
            _isLoading.value = true
            repository.deleteActivity(activityId)
                .onSuccess {
                    android.util.Log.d("디버깅 로그:VM", "삭제 성공! 활동 새로고침")
                    refreshActivities()
                    return true
                }
                .onFailure {
                    android.util.Log.d("디버깅 로그:VM", "삭제 실패: ${it.message}")
                    _errorMessage.value = it.message
                    return false
                }
            false
        } catch (e: Exception) {
            android.util.Log.d("디버깅 로그:VM", "삭제 중 예외 발생: ${e.message}")
            _errorMessage.value = e.message
            false
        } finally {
            _isLoading.value = false
        }
    }
}
