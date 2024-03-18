package com.MaiN.main_android.retrofit

import com.google.gson.annotations.SerializedName

class SsucatchDataclass : ArrayList<SsucatchDataclass.SsucatchDataclassItem>() {
    data class SsucatchDataclassItem(
        @SerializedName("category")
        val category: String,
        @SerializedName("favorites")
        var favorites: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("progress")
        val progress: String,
        @SerializedName("sDate")
        val sDate: String,
        @SerializedName("title")
        val title: String
    )
}