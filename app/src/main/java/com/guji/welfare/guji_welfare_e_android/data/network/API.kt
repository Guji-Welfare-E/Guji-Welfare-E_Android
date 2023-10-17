package com.guji.welfare.guji_welfare_e_android.data.network

import com.guji.welfare.guji_welfare_e_android.data.dto.StatusDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginRequestDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginResponseDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.SignupRequestDto
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    //account
    @POST("/v2/signup")
    fun signup(@Body signupRequestDto: SignupRequestDto): Call<StatusDto>

    @POST("/v2/login")
    fun login(@Body loginRequestDto: LoginRequestDto): Call<LoginResponseDto>

    @GET("/v2/user/get")
    suspend fun getUserData(): UserDataDto
}