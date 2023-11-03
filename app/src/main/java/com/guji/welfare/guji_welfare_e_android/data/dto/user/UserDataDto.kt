package com.guji.welfare.guji_welfare_e_android.data.dto.user


data class UserDataDto(
    val status: Int,
    val data: Data
)

data class Data(
    val name: String,
    val nickName: String,
    val telephoneNum: String,
    val residence: String,
    val birth: String,
    val manager: Any?,
    val guardian: List<String>,
    val disease: List<DiseaseDisorder>,
    val registerTime: String,
    val editTime: String,
    val authority: String
)

data class DiseaseDisorder(
    val name: String,
    val createTime: String
)
