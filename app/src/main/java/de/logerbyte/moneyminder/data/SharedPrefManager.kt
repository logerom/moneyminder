package de.logerbyte.moneyminder.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun writeString(key: String, value: String?) {
        sharedPreferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getSharedPrefString(key: String) = sharedPreferences.getString(key, "")
}
