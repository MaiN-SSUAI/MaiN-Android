package com.MaiN.main_android.View.Reserv.data

import com.MaiN.main_android.retrofit.ReservationRequest

data class ReservationBottomSheetData(
    val location: String,
    val studentId: String,
    val startTime: String,
    val endTime: String,
)

fun ReservationBottomSheetData.toReservationRequest(selectedDate: String) =
    ReservationRequest(
        location = location,
        studentId = studentId,
        startDateTimeStr = "${selectedDate}T${startTime}:00.000+09:00",
        endDateTimeStr = "${selectedDate}T${endTime}:00.000+09:00",
    )

