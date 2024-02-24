package com.MaiN.main_android.retrofit

import com.google.gson.annotations.SerializedName

class ReservDataclass : ArrayList<ReservDataclass.ReservDataclassItem>(){
    data class ReservDataclassItem(
        @SerializedName("end")
        val end: String,
        @SerializedName("end_pixel")
        val end_pixel: String,
        @SerializedName("eventid")
        val eventid: String,
        @SerializedName("start")
        val start: String,
        @SerializedName("start_pixel")
        val start_pixel: String,
        @SerializedName("summary")
        val summary: String
    )
}