package com.woojun.hackathonstudy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RetrofitAPI {
    @GET("keyword.json")
    fun getMapResult(
        @Header("Authorization") key: String,
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String
    ): Call<MapResult>
}