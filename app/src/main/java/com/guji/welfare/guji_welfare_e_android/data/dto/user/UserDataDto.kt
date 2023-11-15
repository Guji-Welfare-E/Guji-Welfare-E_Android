package com.guji.welfare.guji_welfare_e_android.data.dto.user

data class UserDataDto(
    val status: Int,
    val data: UserData
)

data class UserData(
    val name: String,
    val nickName: String,
    val telephoneNum: String,
    val residence: String,
    val birth: String,
    val manager: Manager?,
    val guardian: List<String>,
    val disease: List<DiseaseDisorder>?,
    val registerTime: String,
    val editTime: String,
    val authority: String
)

data class DiseaseDisorder(
    val name: String,
    val createTime: String
)

data class Manager(
    val name: String,
    val telephoneNum: String,
    val belong: String,
    val status: String
)