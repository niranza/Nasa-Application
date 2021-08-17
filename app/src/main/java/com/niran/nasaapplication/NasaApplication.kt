package com.niran.nasaapplication

import android.app.Application
import com.niran.nasaapplication.dataset.retrofit.RetrofitInstance
import com.niran.nasaapplication.repositories.NasaRepository

class NasaApplication : Application() {

    val nasaRepository by lazy { NasaRepository(RetrofitInstance.nasaApi) }

}