package com.MaiN.main_android.retrofit

import com.google.gson.annotations.SerializedName

class FunsysDataclass : ArrayList<FunsysDataclass.FunsysDataclassItem>(){
    data class FunsysDataclassItem(
        @SerializedName("end_date")
        val end_date: String,
        @SerializedName("favorites")
        var favorites: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("title")
        val title: String
    )
}