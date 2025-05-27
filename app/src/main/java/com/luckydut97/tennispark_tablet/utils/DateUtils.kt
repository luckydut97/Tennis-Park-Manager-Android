package com.luckydut97.tennispark_tablet.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜와 시간 관련 유틸리티 함수들을 제공하는 객체
 */
object DateUtils {

    private val locale = Locale.getDefault()

    // ==================== 현재 시간/날짜 ====================

    /**
     * 현재 시간을 HH:mm 포맷으로 반환
     * @return "14:30" 형태의 시간 문자열
     */
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(Constants.TIME_FORMAT, locale)
        return formatter.format(Date())
    }

    /**
     * 현재 날짜를 yyyy-MM-dd 포맷으로 반환
     * @return "2024-03-15" 형태의 날짜 문자열
     */
    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat(Constants.DATE_FORMAT, locale)
        return formatter.format(Date())
    }

    /**
     * 현재 요일을 한글로 반환 (일, 월, 화, ...)
     * @return 한글 요일 문자열
     */
    fun getCurrentDayOfWeek(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return Constants.WEEK_DAYS[dayOfWeek - 1]
    }

    /**
     * 상태바용 시간 포맷 (9:41 AM)
     * @return AM/PM 포함 시간 문자열
     */
    fun getStatusBarTime(): String {
        val formatter = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        return formatter.format(Date())
    }

    /**
     * 상태바용 날짜 포맷 (Tue Sep 24)
     * @return 영문 요일과 월 포함 날짜 문자열
     */
    fun getStatusBarDate(): String {
        val formatter = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH)
        return formatter.format(Date())
    }

    /**
     * 시간 문자열이 유효한지 확인 (HH:mm 포맷)
     * @param time 확인할 시간 문자열
     * @return 유효성 여부
     */
    fun isValidTimeFormat(time: String): Boolean {
        return try {
            val formatter = SimpleDateFormat(Constants.TIME_FORMAT, locale)
            formatter.isLenient = false
            formatter.parse(time)
            time.matches(Regex(Constants.Validation.TIME_REGEX))
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 시작 시간이 종료 시간보다 이른지 확인
     * @param startTime 시작 시간 ("HH:mm" 형태)
     * @param endTime 종료 시간 ("HH:mm" 형태)
     * @return 유효한 시간 범위인지 여부
     */
    fun isValidTimeRange(startTime: String, endTime: String): Boolean {
        return try {
            val formatter = SimpleDateFormat(Constants.TIME_FORMAT, locale)
            val start = formatter.parse(startTime)
            val end = formatter.parse(endTime)
            start?.before(end) ?: false
        } catch (e: Exception) {
            false
        }
    }
}