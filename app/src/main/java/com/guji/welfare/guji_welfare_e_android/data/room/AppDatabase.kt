package com.guji.welfare.guji_welfare_e_android.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.disease.dao.DiseaseDao
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.dao.GuardiansDao
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians

@Database(entities = [Guardians::class, Disease::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun guardiansDao(): GuardiansDao
    abstract fun diseaseDao(): DiseaseDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null)
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "park.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}