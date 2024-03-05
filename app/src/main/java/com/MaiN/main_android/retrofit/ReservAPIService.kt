package com.MaiN.main_android.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservAPIService {
    @GET("calendar/show_event")
    fun showEvents(
        @Query("date") date: String,
        @Query("location") location: String
    ): Call<ReservDataclass>

    @POST("calendar/add")
    fun addEvent(
        @Body reservationRequest: ReservationRequest
    ): Call<Void>

    @HTTP(method = "DELETE", path = "/calendar/delete/{eventid}")
    fun deleteEvent(
        @Path("eventid") eventId: String,
    ): Call<Unit>
}

