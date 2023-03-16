package com.mportog.guidanceprojecttest.data.service

import com.mportog.guidanceprojecttest.model.dto.QuoteDto
import com.mportog.guidanceprojecttest.model.dto.UserDto
import retrofit2.http.GET

internal interface FlowService {

    @GET("https://httpstat.us/Random/100-561")
    suspend fun getRandomStatusCode(): String

    @GET("https://httpstat.us/200?delay=200")
    suspend fun getOKStatusCodeWithdelay(): String

    @GET("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/users/")
    suspend fun getUsersList(): List<UserDto>

    @GET("https://api.kanye.rest/")
    suspend fun getKanyeQuote(): QuoteDto
}