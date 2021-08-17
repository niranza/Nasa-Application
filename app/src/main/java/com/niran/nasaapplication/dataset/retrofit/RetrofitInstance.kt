package com.niran.nasaapplication.dataset.retrofit

import com.niran.nasaapplication.dataset.retrofit.api.NasaApi
import com.niran.nasaapplication.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val nasaApi: NasaApi by lazy { retrofit.create(NasaApi::class.java) }
}