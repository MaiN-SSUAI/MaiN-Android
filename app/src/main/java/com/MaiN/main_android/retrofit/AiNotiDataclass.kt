package com.MaiN.main_android.retrofit

import com.google.gson.annotations.SerializedName

class AiNotiDataclass : ArrayList<AiNotiDataclass.AiNotiDataclassItem>(){
    data class AiNotiDataclassItem(
        @SerializedName("date")
        val date: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("favorites")
        var favorites: Boolean
    )
}