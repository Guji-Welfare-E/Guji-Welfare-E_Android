package com.guji.welfare.guji_welfare_e_android.data.room.disease.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Disease(
    @PrimaryKey
    val index: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("date")
    val date: String,
)