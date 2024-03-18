package com.MaiN.main_android.retrofit

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AiNotiAPIService {

    @GET("ainoti/favorites/all/{studentid}")
    suspend fun getAiNotiAll(@Path("studentid") studnetid: String): Response<List<AiNotiDataclass.AiNotiDataclassItem>>

    @POST("ainoti/favorites/add/{studentId}/{aiNotiId}")
    suspend fun addFavorite(
        @Path("studentId") studnetId: String,
        @Path("aiNotiId") aiNotiId: Int
    ): Response<Unit>

    @DELETE("/ainoti/favorites/delete/{studentId}/{aiNotiId}")
    suspend fun deleteFavorite(
        @Path("studentId") studnetId: String,
        @Path("aiNotiId") aiNotiId: Int
    ): Response<Unit>
}
