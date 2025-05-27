package com.luckydut97.tennispark_tablet.data.repository

import com.luckydut97.tennispark_tablet.data.model.Activity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 활동 데이터를 관리하는 Repository 클래스
 * 실제 구현에서는 Room DB나 Retrofit API와 연동됩니다.
 */
class ActivityRepository {

    private val _activities = MutableStateFlow<List<Activity>>(emptyList())
    val activities: Flow<List<Activity>> = _activities.asStateFlow()

    init {
        // 초기 더미 데이터 로드
        loadInitialData()
    }

    /**
     * 초기 더미 데이터 설정
     */
    private fun loadInitialData() {
        val sampleActivities = listOf(
            Activity(
                id = "1",
                location = "수도권 테니스장",
                court = "B코트",
                startTime = "10:00",
                endTime = "12:00",
                selectedDays = listOf("월", "수", "금"),
                address = "서울 강남구 개포로 410 수도전기공고등학교",
                isRepeating = true,
                createdAt = System.currentTimeMillis() - 86400000 // 1일 전
            ),
            Activity(
                id = "2",
                location = "수도권 테니스장",
                court = "A코트",
                startTime = "14:00",
                endTime = "16:00",
                selectedDays = listOf("화", "목"),
                address = "서울 강남구 개포로 410 수도전기공고등학교",
                isRepeating = true,
                createdAt = System.currentTimeMillis() - 172800000 // 2일 전
            ),
            Activity(
                id = "3",
                location = "강남 테니스장",
                court = "C코트",
                startTime = "18:00",
                endTime = "20:00",
                selectedDays = listOf("토", "일"),
                address = "서울 강남구 테헤란로 123",
                isRepeating = false,
                createdAt = System.currentTimeMillis() - 259200000 // 3일 전
            )
        )
        _activities.value = sampleActivities
    }

    /**
     * 모든 활동 목록 조회
     */
    suspend fun getActivities(): Result<List<Activity>> {
        return try {
            delay(300) // 네트워크 지연 시뮬레이션
            Result.success(_activities.value.filter { it.isActive })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * ID로 특정 활동 조회
     */
    suspend fun getActivityById(id: String): Result<Activity?> {
        return try {
            delay(200)
            val activity = _activities.value.find { it.id == id && it.isActive }
            Result.success(activity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 새로운 활동 생성
     */
    suspend fun createActivity(activity: Activity): Result<Activity> {
        return try {
            delay(500)

            // 유효성 검사
            if (!activity.isValid) {
                return Result.failure(IllegalArgumentException("활동 정보가 유효하지 않습니다."))
            }

            val newActivity = activity.copy(
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )

            _activities.value = _activities.value + newActivity
            Result.success(newActivity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 활동 정보 수정
     */
    suspend fun updateActivity(activity: Activity): Result<Activity> {
        return try {
            delay(400)

            if (!activity.isValid) {
                return Result.failure(IllegalArgumentException("활동 정보가 유효하지 않습니다."))
            }

            val currentList = _activities.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == activity.id }

            if (index == -1) {
                return Result.failure(NoSuchElementException("활동을 찾을 수 없습니다."))
            }

            val updatedActivity = activity.copy(updatedAt = System.currentTimeMillis())
            currentList[index] = updatedActivity
            _activities.value = currentList

            Result.success(updatedActivity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 활동 삭제 (소프트 삭제)
     */
    suspend fun deleteActivity(id: String): Result<Unit> {
        return try {
            delay(300)

            val currentList = _activities.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == id }

            if (index == -1) {
                return Result.failure(NoSuchElementException("활동을 찾을 수 없습니다."))
            }

            // 소프트 삭제: isActive를 false로 변경
            currentList[index] = currentList[index].copy(
                isActive = false,
                updatedAt = System.currentTimeMillis()
            )
            _activities.value = currentList

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 특정 장소의 활동 목록 조회
     */
    suspend fun getActivitiesByLocation(location: String): Result<List<Activity>> {
        return try {
            delay(250)
            val filteredActivities = _activities.value.filter {
                it.location == location && it.isActive
            }
            Result.success(filteredActivities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 특정 요일의 활동 목록 조회
     */
    suspend fun getActivitiesByDay(day: String): Result<List<Activity>> {
        return try {
            delay(250)
            val filteredActivities = _activities.value.filter {
                it.selectedDays.contains(day) && it.isActive
            }
            Result.success(filteredActivities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 활동 검색
     */
    suspend fun searchActivities(query: String): Result<List<Activity>> {
        return try {
            delay(300)
            val searchResults = _activities.value.filter { activity ->
                activity.isActive && (
                        activity.location.contains(query, ignoreCase = true) ||
                                activity.court.contains(query, ignoreCase = true) ||
                                activity.address.contains(query, ignoreCase = true)
                        )
            }
            Result.success(searchResults)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 활동 통계 조회
     */
    suspend fun getActivityStats(): Result<ActivityStats> {
        return try {
            delay(200)
            val activeActivities = _activities.value.filter { it.isActive }
            val stats = ActivityStats(
                totalActivities = activeActivities.size,
                totalLocations = activeActivities.map { it.location }.distinct().size,
                totalCourts = activeActivities.map { "${it.location}-${it.court}" }.distinct().size,
                repeatingActivities = activeActivities.count { it.isRepeating },
                oneTimeActivities = activeActivities.count { !it.isRepeating }
            )
            Result.success(stats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

/**
 * 활동 통계 데이터 클래스
 */
data class ActivityStats(
    val totalActivities: Int = 0,
    val totalLocations: Int = 0,
    val totalCourts: Int = 0,
    val repeatingActivities: Int = 0,
    val oneTimeActivities: Int = 0
)