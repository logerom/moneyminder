package de.logerbyte.moneyminder.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun writeSharedPrefInt(key: String, value: Int) {
        sharedPreferences.edit().apply {
            putInt(key, value)
            apply()
        }
    }

    fun getSharedPrefInt(key: String) = sharedPreferences.getInt(key, 0)
}
