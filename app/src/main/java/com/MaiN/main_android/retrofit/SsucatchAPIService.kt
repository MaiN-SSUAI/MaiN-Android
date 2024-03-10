package com.MaiN.main_android.retrofit

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SsucatchAPIService {
    @GET("ssucatchnoti/favorites/all/{studentid}")
    suspend fun getSsucatchNotiAll(@Path("studentid") studnetid: String): Response<List<SsucatchDataclass.SsucatchDataclassItem>>

    @POST("ssucatchnoti/favorites/add/{studentId}/{ssucatchNotiId}")
    suspend fun addFavorite(
        @Path("studentId") studnetId: String,
        @Path("ssucatchNotiId") ssucatchNotiId: Int
    ): Response<Unit>

    @DELETE("/ssucatchnoti/favorites/delete/{studentId}/{ssucatchNotiId}")
    suspend fun deleteFavorite(
        @Path("studentId") studnetId: String,
        @Path("ssucatchNotiId") ssucatchNotiId: Int
    ): Response<Unit>

}