package com.niran.nasaapplication.dataset.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nasa_picture_table")
data class NasaPicture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nasa_id")
    val nasaId: Long = 0L,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?
)