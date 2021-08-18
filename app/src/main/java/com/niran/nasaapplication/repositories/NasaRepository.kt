package com.niran.nasaapplication.repositories

import com.niran.nasaapplication.dataset.database.daos.NasaPictureDao
import com.niran.nasaapplication.dataset.models.NasaPicture
import com.niran.nasaapplication.dataset.retrofit.api.NasaApi

class NasaRepository(private val api: NasaApi, private val dao: NasaPictureDao) {

    val savedNasaPictureListWithFlow = dao.getAllNasaPicturesWithFlow()

    suspend fun insertNasaPicture(nasaPicture: NasaPicture) = dao.insertNasaPicture(nasaPicture)

    suspend fun deleteNasaPicture(nasaPicture: NasaPicture) = dao.deleteNasaPicture(nasaPicture)

    suspend fun getNasaRandomPictures(count: Int) = api.getRandomNasaPictures(count)
}