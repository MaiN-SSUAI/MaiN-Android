package com.MaiN.main_android.retrofit

import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//레트로핏 객체를 생성하는 클래스
class RetrofitConnection {


    companion object {
        //        private const val BASE_URL = "http://10.0.2.2:8080/"
        private const val BASE_URL = "http://43.203.195.189/"
        private var INSTANCE: Retrofit? = null

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = okhttp3.OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()


        fun getInstance(): Retrofit {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                    .build()
            }
            return INSTANCE!!
        }
    }
}