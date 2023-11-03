package com.guji.welfare.guji_welfare_e_android.data.room.guardians.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians

@Dao
interface GuardiansDao {
    @Query("SELECT * FROM Guardians")
    fun getAll(): List<Guardians>

    @Query("DELETE FROM Guardians")
    fun deleteAll()

    @Insert
    fun insertItem(item: Guardians)
    @Delete
    fun delete(guardians: Guardians)
}