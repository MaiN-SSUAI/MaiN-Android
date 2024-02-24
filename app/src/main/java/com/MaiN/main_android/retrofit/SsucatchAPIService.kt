package com.MaiN.main_android.retrofit

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SsucatchAPIService {
    @GET("ssucatchnoti/favorites/all/{studentid}")
    fun getSsucatchNotiAll(@Path("studentid") studnetid :String) : Call<List<SsucatchDataclass.SsucatchDataclassItem>>

    @POST("ssucatchnoti/favorites/add/{studentId}/{ssucatchNotiId}")
    fun addFavorite(@Path("studentId") studnetId: String, @Path("ssucatchNotiId") ssucatchNotiId : Int):Call<Void>

    @DELETE("/ssucatchnoti/favorites/delete/{studentId}/{ssucatchNotiId}")
    fun deleteFavorite(@Path("studentId")studnetId: String,@Path("ssucatchNotiId")ssucatchNotiId: Int):Call<Void>

}