package com.guji.welfare.guji_welfare_e_android.data.dto.user


data class UserDataDto(
    val status: Int,
    val data: Data
)

data class Data(
    val uuid: String,
    val name: String,
    val telephoneNum: String,
    val residence: String,
    val birth: String,
    val disease: List<DiseaseDisorder>,
    val registerTime: String,
    val editTime: String,
    val authority: String
)

data class DiseaseDisorder(
    val name: String,
    val createTime: String
)
