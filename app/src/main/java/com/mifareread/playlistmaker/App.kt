package com.mifareread.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_PREFERENCES = "playlist_preferences"
const val SWITCH_THEME_KEY = "key_for_switch_theme"

class App : Application() {

    var darkTheme = false
    lateinit var sharedPreferences :SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(PLAYLIST_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(SWITCH_THEME_KEY, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(isDarkTheme : Boolean){
        darkTheme = isDarkTheme
        AppCompatDelegate.setDefaultNightMode(
            if(darkTheme){ AppCompatDelegate.MODE_NIGHT_YES}
            else{ AppCompatDelegate.MODE_NIGHT_NO }
        )

        sharedPreferences.edit()
            .putBoolean(SWITCH_THEME_KEY, darkTheme)
            .apply()
    }
}