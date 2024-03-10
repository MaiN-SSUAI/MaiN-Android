package com.MaiN.main_android.retrofit

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FunsysAPIService {
    @GET("funsysnoti/favorites/all/{studentid}")
    suspend fun getFunsysNotiAll(@Path("studentid") studnetid: String): Response<List<FunsysDataclass.FunsysDataclassItem>>

    @POST("funsysnoti/favorites/add/{studentId}/{funsysNotiId}")
    suspend fun addFavorite(
        @Path("studentId") studnetId: String,
        @Path("funsysNotiId") funsysNotiId: Int
    ): Response<Unit>

    @DELETE("/funsysnoti/favorites/delete/{studentId}/{funsysNotiId}")
    suspend fun deleteFavorite(
        @Path("studentId") studnetId: String,
        @Path("funsysNotiId") funsysNotiId: Int
    ): Response<Unit>

}