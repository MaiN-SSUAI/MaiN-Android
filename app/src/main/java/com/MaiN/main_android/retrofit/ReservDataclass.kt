package com.MaiN.main_android.retrofit

import com.MaiN.main_android.View.Reserv.state.CellUiState
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

class ReservDataclass : ArrayList<ReservDataclass.ReservDataclassItem>() {
    data class ReservDataclassItem(
        @SerializedName("end")
        val end: String,
//        @SerializedName("end_pixel")
//        val end_pixel: String,
        @SerializedName("eventid")
        val eventid: String,
        @SerializedName("start")
        val start: String,
//        @SerializedName("start_pixel")
//        val start_pixel: String,
        @SerializedName("summary")
        val summary: String
    )
}

fun ReservDataclass.toCellUiStateList() = map {
    CellUiState(
        startHour = convertIsoDateTimeToHour(it.start),
        startMin = convertIsoDateTimeToMinute(it.start),
        endHour = convertIsoDateTimeToHour(it.end),
        endMin = convertIsoDateTimeToMinute(it.end),
        summary = it.summary,
        koreaStyleTime = convertIsoDateTimeToKoreanDate(it.start),
        eventId = it.eventid
    )
}

fun convertIsoDateTimeToHour(isoDateTime: String): Int {
    val customFormatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        .appendOffset("+HH:MM", "+00:00") // Handles different offset formats
        .toFormatter()

    val localDateTime =
        LocalDateTime.parse(isoDateTime, customFormatter)
    return localDateTime.hour
}

fun convertIsoDateTimeToMinute(isoDateTime: String): Int {
    val customFormatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        .appendOffset("+HH:MM", "+00:00") // Handles different offset formats
        .toFormatter()

    val localDateTime =
        LocalDateTime.parse(isoDateTime, customFormatter)
    return localDateTime.minute
}

fun convertIsoDateTimeToKoreanDate(isoDateTime: String): String {
    // Parse the ISO_DATE_TIME string to a ZonedDateTime object
    val zonedDateTime = ZonedDateTime.parse(isoDateTime)

    // Convert time zone to Korea Standard Time (KST)
    val koreanTimeZone = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"))

    // Define a custom pattern for the Korean date format
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)

    // Format and return the date in Korean format
    return koreanTimeZone.format(formatter)
}
