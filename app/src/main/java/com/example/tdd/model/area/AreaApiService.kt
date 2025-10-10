package com.example.tdd.model.area

import retrofit2.http.GET
import retrofit2.http.Query

interface AreaApiService {
    @GET("B551011/KorService2/areaCode2")
    suspend fun getAreaCode(
        @Query("MobileOS")
        mobileOS: String,
        @Query("MobileApp")
        mobileApp: String,
        @Query("_type")
        type: String = "json",
        @Query("serviceKey")
        serviceKey: String,
    ): AreaDto
}