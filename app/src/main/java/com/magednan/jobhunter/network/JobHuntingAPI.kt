package com.magednan.jobhunter.network

import com.magednan.jobhunter.models.Jobs
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobHuntingAPI {

    @GET("remote-jobs")
    suspend fun getRemoteJob(): Response<Jobs>

    @GET("remote-jobs")
    suspend fun searchRemoteJob(@Query("search") query: String?): Response<Jobs>

}