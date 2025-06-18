package com.luckydut97.tennispark_tablet.utils

object Constants {

    // ==================== 앱 정보 ====================
    const val APP_NAME = "Tennis Park Tablet"
    const val APP_VERSION = "1.0.0"

    // ==================== 화면 관련 ====================
    const val TABLET_WIDTH_DP = 1600
    const val TABLET_HEIGHT_DP = 2560
    const val SPLASH_DELAY_MS = 2000L

    // ==================== 포인트 시스템 ====================
    const val CHECK_IN_POINTS = 100
    const val EVENT_CREATE_POINTS = 100
    const val ACTIVITY_CREATE_POINTS = 50
    const val TOURNAMENT_WIN_POINTS = 500
    const val DAILY_BONUS_POINTS = 10

    // ==================== 시간 포맷 ====================
    const val TIME_FORMAT = "HH:mm"
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATETIME_FORMAT = "yyyy-MM-dd HH:mm"
    const val DISPLAY_DATE_FORMAT = "MM월 dd일"
    const val DISPLAY_DATETIME_FORMAT = "MM월 dd일 HH:mm"

    // ==================== 테니스 장소 및 코트 ====================
    val TENNIS_LOCATIONS = listOf(
        "수도권 테니스장",
        "강남 테니스장",
        "서초 테니스장",
        "송파 테니스장",
        "마포 테니스장"
    )

    val COURT_TYPES = listOf(
        "A코트",
        "B코트",
        "C코트",
        "D코트"
    )

    // ==================== 요일 ====================
    val WEEK_DAYS = listOf("일", "월", "화", "수", "목", "금", "토")
    val WEEK_DAYS_FULL = listOf("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일")

    // ==================== 배너 크기 ====================
    val BANNER_SIZES = mapOf(
        "메인화면" to "(300X100)",
        "활동시장" to "(300X100)",
        "구매목록" to "(300X50)",
        "회원정보" to "(300X50)"
    )

    // ==================== 기본값 ====================
    object Defaults {
        const val DEFAULT_LOCATION = "수도권 테니스장"
        const val DEFAULT_COURT = "A코트"
        const val DEFAULT_START_TIME = "10:00"
        const val DEFAULT_END_TIME = "12:00"
        const val DEFAULT_ADDRESS = "서울 강남구 개포로 410"
        val DEFAULT_SELECTED_DAYS = listOf("토")
        const val DEFAULT_IS_REPEATING = true
    }

    // ==================== 네트워크 및 API ====================
    object Network {
        const val BASE_URL = "https://api.tennispark.com/"
        const val CONNECT_TIMEOUT = 30L // seconds
        const val READ_TIMEOUT = 30L // seconds
        const val WRITE_TIMEOUT = 30L // seconds

        // API Endpoints
        const val ACTIVITIES_ENDPOINT = "activities"
        const val EVENTS_ENDPOINT = "events"
        const val BANNERS_ENDPOINT = "banners"
        const val USERS_ENDPOINT = "users"
        const val CHECK_IN_ENDPOINT = "checkin"
    }

    // ==================== 로컬 저장소 ====================
    object Storage {
        const val SHARED_PREF_NAME = "tennis_park_prefs"
        const val DATABASE_NAME = "tennis_park_db"
        const val DATABASE_VERSION = 1

        // SharedPreferences Keys
        const val KEY_USER_ID = "user_id"
        const val KEY_LAST_CHECK_IN = "last_check_in"
        const val KEY_TOTAL_POINTS = "total_points"
        const val KEY_APP_FIRST_RUN = "app_first_run"
        const val KEY_SELECTED_LOCATION = "selected_location"
        const val KEY_SELECTED_COURT = "selected_court"
    }

    // ==================== 에러 메시지 ====================
    object ErrorMessages {
        const val NETWORK_ERROR = "네트워크 연결을 확인해주세요."
        const val UNKNOWN_ERROR = "알 수 없는 오류가 발생했습니다."
        const val VALIDATION_ERROR = "입력 정보를 확인해주세요."
        const val NOT_FOUND_ERROR = "요청한 정보를 찾을 수 없습니다."
        const val SERVER_ERROR = "서버 오류가 발생했습니다."
        const val TIMEOUT_ERROR = "요청 시간이 초과되었습니다."

        // Activity 관련
        const val ACTIVITY_CREATE_ERROR = "활동 생성에 실패했습니다."
        const val ACTIVITY_UPDATE_ERROR = "활동 수정에 실패했습니다."
        const val ACTIVITY_DELETE_ERROR = "활동 삭제에 실패했습니다."

        // Event 관련
        const val EVENT_CREATE_ERROR = "이벤트 생성에 실패했습니다."
        const val EVENT_UPDATE_ERROR = "이벤트 수정에 실패했습니다."
        const val EVENT_DELETE_ERROR = "이벤트 삭제에 실패했습니다."

        // Banner 관련
        const val BANNER_UPLOAD_ERROR = "배너 업로드에 실패했습니다."
        const val BANNER_UPDATE_ERROR = "배너 수정에 실패했습니다."
    }

    // ==================== 성공 메시지 ====================
    object SuccessMessages {
        const val ACTIVITY_CREATED = "활동이 생성되었습니다."
        const val ACTIVITY_UPDATED = "활동이 수정되었습니다."
        const val ACTIVITY_DELETED = "활동이 삭제되었습니다."

        const val EVENT_CREATED = "이벤트가 생성되었습니다."
        const val EVENT_UPDATED = "이벤트가 수정되었습니다."
        const val EVENT_DELETED = "이벤트가 삭제되었습니다."

        const val BANNER_UPLOADED = "배너가 업로드되었습니다."
        const val BANNER_UPDATED = "배너가 수정되었습니다."

        const val CHECK_IN_SUCCESS = "출석 체크가 완료되었습니다."
        const val POINTS_EARNED = "포인트가 지급되었습니다."
    }

    // ==================== 유효성 검사 ====================
    object Validation {
        const val MIN_ACTIVITY_TITLE_LENGTH = 2
        const val MAX_ACTIVITY_TITLE_LENGTH = 50
        const val MIN_EVENT_TITLE_LENGTH = 2
        const val MAX_EVENT_TITLE_LENGTH = 100
        const val MAX_EVENT_CONTENT_LENGTH = 500
        const val MIN_POINTS = 1
        const val MAX_POINTS = 10000
        const val MAX_ADDRESS_LENGTH = 200

        // 정규표현식
        const val TIME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$"
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
        const val PHONE_REGEX = "^01[0-9]-?[0-9]{4}-?[0-9]{4}$"
    }

    // ==================== 애니메이션 ====================
    object Animation {
        const val FAST_DURATION = 150
        const val NORMAL_DURATION = 300
        const val SLOW_DURATION = 500
        const val SPLASH_FADE_DURATION = 1000

        // Transition Names
        const val SHARED_ELEMENT_CARD = "shared_card"
        const val SHARED_ELEMENT_FAB = "shared_fab"
    }

    // ==================== 디자인 상수 ====================
    object Design {
        // Spacing
        const val SPACING_TINY = 4
        const val SPACING_SMALL = 8
        const val SPACING_MEDIUM = 16
        const val SPACING_LARGE = 24
        const val SPACING_XLARGE = 32
        const val SPACING_XXLARGE = 40

        // Corner Radius
        const val CORNER_RADIUS_SMALL = 8
        const val CORNER_RADIUS_MEDIUM = 12
        const val CORNER_RADIUS_LARGE = 16
        const val CORNER_RADIUS_XLARGE = 20

        // Elevation
        const val ELEVATION_SMALL = 2
        const val ELEVATION_MEDIUM = 4
        const val ELEVATION_LARGE = 8
        const val ELEVATION_XLARGE = 16

        // Button Heights
        const val BUTTON_HEIGHT_SMALL = 36
        const val BUTTON_HEIGHT_MEDIUM = 48
        const val BUTTON_HEIGHT_LARGE = 56
        const val BUTTON_HEIGHT_XLARGE = 64
    }

    // ==================== 추가 상수 ====================
    const val BASE_URL = "https://tennis-park.store/"
    const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImV4cCI6MjExMDA3NzIzMCwiaWF0IjoxNzUwMDc3MjMwfQ.lHmvLsdorChZ82oM5V9n-hNaUXQbFT8LV05-6N48V3g"
}
