package com.MaiN.main_android.retrofit

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AiNotiAPIService {

    @GET("ainoti/favorites/all/{studentid}")
    fun getAiNotiAll(@Path("studentid") studnetid :String): Call<List<AiNotiDataclass.AiNotiDataclassItem>>

    @POST("ainoti/favorites/add/{studentId}/{aiNotiId}")
    fun addFavorite(@Path("studentId") studnetId: String, @Path("aiNotiId") aiNotiId : Int):Call<Void>

    @DELETE("/ainoti/favorites/delete/{studentId}/{aiNotiId}")
    fun deleteFavorite(@Path("studentId")studnetId: String,@Path("aiNotiId")aiNotiId: Int):Call<Void>
}
