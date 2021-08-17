package com.niran.nasaapplication.dataset.retrofit.api

import com.niran.nasaapplication.dataset.models.NasaRandomPictures
import com.niran.nasaapplication.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getRandomNasaPictures(
        @Query("count")
        count: Int = 1,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<NasaRandomPictures>

}