package com.niran.nasaapplication.dataset.database.daos

import androidx.room.*
import com.niran.nasaapplication.dataset.models.NasaPicture
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaPictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNasaPicture(nasaPicture: NasaPicture)

    @Query("SELECT * FROM nasa_picture_table")
    fun getAllNasaPicturesWithFlow(): Flow<List<NasaPicture>>

    @Delete
    suspend fun deleteNasaPicture(nasaPicture: NasaPicture)

    @Query("DELETE FROM nasa_picture_table")
    suspend fun deleteAllNasaPictures()
}