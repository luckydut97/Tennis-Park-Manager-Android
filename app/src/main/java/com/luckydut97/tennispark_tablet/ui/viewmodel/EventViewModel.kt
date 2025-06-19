package com.luckydut97.tennispark_tablet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckydut97.tennispark_tablet.data.repository.EventRepository
import com.luckydut97.tennispark_tablet.data.network.event.EventItem
import com.luckydut97.tennispark_tablet.data.network.event.EventRequest
import com.luckydut97.tennispark_tablet.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _eventList = MutableStateFlow<List<EventItem>>(emptyList())
    val eventList: StateFlow<List<EventItem>> = _eventList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchEvents() = viewModelScope.launch {
        _isLoading.value = true
        val result = eventRepository.getEventList(Constants.ACCESS_TOKEN)
        _isLoading.value = false
        result.onSuccess { _eventList.value = it }
            .onFailure { _errorMessage.value = it.message ?: "네트워크 오류" }
    }

    fun createEvent(title: String, content: String, point: String) = viewModelScope.launch {
        Log.d("EVENT 디버깅:", "[등록 버튼 클릭] title=$title, content=$content, point=$point")
        _isLoading.value = true
        if (title.isBlank() || content.isBlank() || point.isBlank() || point.toIntOrNull() == null || point.toIntOrNull()!! <= 0) {
            Log.e("EVENT 디버깅:", "[등록 필수값 누락] title=$title, content=$content, point=$point")
            _errorMessage.value = "모든 필드를 올바르게 입력하세요."
            _isLoading.value = false
            return@launch
        }
        val body = EventRequest(title, content, point.toIntOrNull() ?: 0)
        val result = eventRepository.createEvent(Constants.ACCESS_TOKEN, body)
        _isLoading.value = false
        if (result.isSuccess) fetchEvents() else _errorMessage.value = result.exceptionOrNull()?.message ?: "등록 실패"
    }

    fun updateEvent(id: Long, title: String, content: String, point: String) =
        viewModelScope.launch {
            Log.d("EVENT 디버깅:", "[수정 버튼 클릭] id=$id, title=$title, content=$content, point=$point")
        _isLoading.value = true
        if (title.isBlank() || content.isBlank() || point.isBlank() || point.toIntOrNull() == null || point.toIntOrNull()!! <= 0) {
            Log.e("EVENT 디버깅:", "[수정 필수값 누락] id=$id, title=$title, content=$content, point=$point")
            _errorMessage.value = "모든 필드를 올바르게 입력하세요."
            _isLoading.value = false
            return@launch
        }
        val body = EventRequest(title, content, point.toIntOrNull() ?: 0)
        val result = eventRepository.updateEvent(Constants.ACCESS_TOKEN, id, body)
        _isLoading.value = false
        if (result.isSuccess) fetchEvents() else _errorMessage.value = result.exceptionOrNull()?.message ?: "수정 실패"
    }

    fun deleteEvent(id: Long) = viewModelScope.launch {
        Log.d("EVENT 디버깅:", "[삭제 버튼 클릭] id=$id")
        _isLoading.value = true
        val result = eventRepository.deleteEvent(Constants.ACCESS_TOKEN, id)
        _isLoading.value = false
        if (result.isSuccess) fetchEvents() else _errorMessage.value = result.exceptionOrNull()?.message ?: "삭제 실패"
    }
}
