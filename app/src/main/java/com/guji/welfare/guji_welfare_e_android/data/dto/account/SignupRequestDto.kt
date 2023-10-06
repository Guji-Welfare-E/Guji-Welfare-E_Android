package com.guji.welfare.guji_welfare_e_android.data.dto.account

data class SignupRequestDto(
    val name: String,
    val birth: String,
    val password: String,
    val telephoneNum: String,
    val residence: String
)
