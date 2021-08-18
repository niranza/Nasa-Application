package com.niran.nasaapplication.dataset.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.niran.nasaapplication.dataset.database.daos.NasaPictureDao
import com.niran.nasaapplication.dataset.models.NasaPicture

@Database(entities = [NasaPicture::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val nasaPictureDao: NasaPictureDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db"
            )
                .fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationOnDowngrade()
                .build().also { INSTANCE = it }
        }
    }
}