package com.niran.nasaapplication.repositories

import com.niran.nasaapplication.dataset.retrofit.api.NasaApi

class NasaRepository(private val api: NasaApi) {

    suspend fun getNasaRandomPictures(count: Int) = api.getRandomNasaPictures(count)

}