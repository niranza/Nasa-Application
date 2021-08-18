package com.niran.nasaapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.niran.nasaapplication.NasaApplication
import java.io.IOException

class InternetUtil {
    companion object {

        suspend fun <T> MutableLiveData<Resource<T>>.safeRetrofitCall(
            app: NasaApplication,
            request: suspend () -> Unit
        ) {
            postValue(Resource.Loading())
            try {
                if (app.hasInternetConnection()) {
                    request()
                } else {
                    postValue(Resource.Error(Constants.NO_INTERNET_CONNECTION))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> postValue(Resource.Error(Constants.NETWORK_FAILURE))
                    else -> postValue(Resource.Error(Constants.CONVERSION_FAILURE))
                }
            }
        }

        private fun NasaApplication.hasInternetConnection(): Boolean {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetWork = connectivityManager.activeNetwork ?: return false
                val capabilities =
                    connectivityManager.getNetworkCapabilities(activeNetWork) ?: return false
                return when {
                    capabilities.hasTransport(TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.apply {
                    return when (type) {
                        TYPE_WIFI -> true
                        TYPE_MOBILE -> true
                        TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
            return false
        }
    }
}