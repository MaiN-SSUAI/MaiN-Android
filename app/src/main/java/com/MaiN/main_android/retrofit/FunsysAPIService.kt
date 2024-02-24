package com.MaiN.main_android.retrofit

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FunsysAPIService {
    @GET("funsysnoti/favorites/all/{studentid}")
    fun getFunsysNotiAll(@Path("studentid") studnetid :String) : Call<List<FunsysDataclass.FunsysDataclassItem>>

    @POST("funsysnoti/favorites/add/{studentId}/{funsysNotiId}")
    fun addFavorite(@Path("studentId") studnetId: String, @Path("funsysNotiId") funsysNotiId : Int):Call<Void>

    @DELETE("/funsysnoti/favorites/delete/{studentId}/{funsysNotiId}")
    fun deleteFavorite(@Path("studentId")studnetId: String,@Path("funsysNotiId")funsysNotiId: Int):Call<Void>

}