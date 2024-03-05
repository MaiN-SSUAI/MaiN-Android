package com.MaiN.main_android.retrofit

import com.google.gson.annotations.SerializedName

data class ReservationRequest(
    val location: String,
    @SerializedName("student_id")
    val studentId: String,
    val startDateTimeStr: String,
    val endDateTimeStr: String
)
