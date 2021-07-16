package de.logerbyte.moneyminder.domain

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun writeSharedPrefInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()
    fun writeSharedPrefSet(key: String, value: MutableSet<String>) = sharedPreferences.edit().putStringSet(key, value).apply()

    fun getSharedPrefInt(key: String) = sharedPreferences.getInt(key, 0)
}
