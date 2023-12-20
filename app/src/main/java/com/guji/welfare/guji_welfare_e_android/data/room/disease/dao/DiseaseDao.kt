package com.guji.welfare.guji_welfare_e_android.data.room.disease.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {
    @Query("SELECT * FROM Disease")
    fun getAll(): List<Disease>

    @Insert
    fun insertItem(item: Disease)

    @Query("DELETE FROM Disease")
    fun deleteAll()

    @Query("DELETE FROM Disease WHERE `index` = :index")
    fun deleteItemById(index: Int)

    @Delete
    fun delete(disease: Disease)

}