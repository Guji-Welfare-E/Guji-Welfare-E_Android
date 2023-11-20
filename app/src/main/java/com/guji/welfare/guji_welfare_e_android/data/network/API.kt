package com.guji.welfare.guji_welfare_e_android.data.network

import com.guji.welfare.guji_welfare_e_android.data.dto.StatusDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginRequestDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginResponseDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.SignupRequestDto
import com.guji.welfare.guji_welfare_e_android.data.dto.user.GuardianDto
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import com.guji.welfare.guji_welfare_e_android.data.dto.user.WelfareworkerDataDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    //회원가입
    @POST("/v2/signup")
    fun signup(@Body signupRequestDto: SignupRequestDto): Call<StatusDto>

    //로그인
    @POST("/v2/login")
    fun login(@Body loginRequestDto: LoginRequestDto): Call<LoginResponseDto>

    //전화번호 인증
    @POST("v2/sms/auth/request")
    fun telephoneCertification(@Query("telephone") telephone: String)

    //본인 정보 가져오기
    @GET("/v2/user/get")
    suspend fun getUserData(): UserDataDto

    //사용자의 복지사 변경
    @PATCH("/v2/user/update/manager")
    suspend fun updateManagerData(@Query("manager") manager: String): WelfareworkerDataDto

    //사용자의 보호자 일괄 변경
    @PATCH("v2/user/update/guardians")
    suspend fun updateGuardiansData(@Body guardianDto: List<GuardianDto>): StatusDto

    //사용자 별칭 랜덤 변경
    @PATCH("v2/user/update/nickname")
    suspend fun updateNickName()
}