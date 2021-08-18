package com.niran.nasaapplication.utils

import android.content.Context

class SharedPrefUtil {
    companion object {
        fun Context.setSharedPrefBoolean(
            fileKeyId: Int,
            valueKey: Int,
            boolean: Boolean
        ) {
            getSharedPreferences(getString(fileKeyId), Context.MODE_PRIVATE).edit()
                .putBoolean(getString(valueKey), boolean).apply()
        }

        fun Context.getSharedPrefBoolean(
            fileKeyId: Int,
            valueKey: Int,
            defaultValue: Boolean
        ): Boolean =
            getSharedPreferences(getString(fileKeyId), Context.MODE_PRIVATE)
                .getBoolean(getString(valueKey), defaultValue)
    }
}