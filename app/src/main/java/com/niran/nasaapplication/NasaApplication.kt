package com.niran.nasaapplication

import android.app.Application
import com.niran.nasaapplication.dataset.database.AppDatabase
import com.niran.nasaapplication.dataset.retrofit.RetrofitInstance
import com.niran.nasaapplication.repositories.NasaRepository

class NasaApplication : Application() {

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    val nasaRepository by lazy { NasaRepository(RetrofitInstance.nasaApi, database.nasaPictureDao) }

}