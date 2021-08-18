package com.niran.nasaapplication.dataset.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "nasa_picture_table")
data class NasaPicture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nasa_id")
    val nasaId: Long = 0L,
    val date: String?,
    val explanation: String?,
    @SerializedName("hdurl")
    @ColumnInfo(name = "hd_photo_url")
    val hdPhotoUrl: String?,
    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String?,
    @SerializedName("service_version")
    @ColumnInfo(name = "service_version")
    val serviceVersion: String?,
    val title: String?,
    @SerializedName("url")
    @ColumnInfo(name = "photo_url")
    val photoUrl: String?
) : Serializable