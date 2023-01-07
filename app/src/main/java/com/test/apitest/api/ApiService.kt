package com.test.apitest.api

import com.test.apitest.utils.Common
import com.test.apitest.models.Populations
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data")
    suspend fun population(@Query("drilldowns") drillDowns: String,@Query("measures") measures: String): Response<Populations>

    companion object {
        var apiService: ApiService? = null

        fun retrofitInstance(): ApiService? {
            return apiService ?: Retrofit.Builder()
                .baseUrl(Common.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
        }
    }
}