package com.luckydut97.tennispark_tablet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckydut97.tennispark_tablet.data.model.MonthlyMemberActivities
import com.luckydut97.tennispark_tablet.data.model.TotalMemberActivities
import com.luckydut97.tennispark_tablet.data.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: AdminRepository) : ViewModel() {

    private val _monthlyStats = MutableStateFlow<MonthlyMemberActivities?>(null)
    val monthlyStats: StateFlow<MonthlyMemberActivities?> get() = _monthlyStats

    private val _totalStats = MutableStateFlow<TotalMemberActivities?>(null)
    val totalStats: StateFlow<TotalMemberActivities?> get() = _totalStats

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchMonthlyMemberActivities() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getMonthlyMemberActivities()
                .onSuccess { stats ->
                    _monthlyStats.value = stats
                    android.util.Log.d("ADMIN VM:", "이번달 회원 활동 로드 성공: $stats")
                }
                .onFailure { error ->
                    _errorMessage.value = error.message
                    android.util.Log.e("ADMIN VM:", "이번달 회원 활동 로드 실패: ${error.message}")
                }

            _isLoading.value = false
        }
    }

    fun fetchTotalMemberActivities() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getTotalMemberActivities()
                .onSuccess { stats ->
                    _totalStats.value = stats
                    android.util.Log.d("ADMIN VM:", "회원 통계 로드 성공: $stats")
                }
                .onFailure { error ->
                    _errorMessage.value = error.message
                    android.util.Log.e("ADMIN VM:", "회원 통계 로드 실패: ${error.message}")
                }

            _isLoading.value = false
        }
    }

    fun fetchAllStats() {
        fetchMonthlyMemberActivities()
        fetchTotalMemberActivities()
    }
}