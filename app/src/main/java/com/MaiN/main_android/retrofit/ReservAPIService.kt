package com.MaiN.main_android.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReservAPIService {
    @GET("calendar/show_event")
    fun showEvents(@Path("date") date:String, @Path("location") location:String): Call<List<ReservDataclass.ReservDataclassItem>>
}