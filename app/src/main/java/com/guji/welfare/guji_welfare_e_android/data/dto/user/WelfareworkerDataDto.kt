package com.guji.welfare.guji_welfare_e_android.data.dto.user

data class WelfareworkerDataDto(
    val status: Int,
    val data: WelfareworkerData
)

data class WelfareworkerData(
    val name: String,
    val telephoneNum: String,
    val belong: String,
    val status: String
)