package com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Guardians(
    @PrimaryKey
    val index: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("telephoneNum")
    val telephoneNum: String,
    @ColumnInfo("info")
    val info: String
)
