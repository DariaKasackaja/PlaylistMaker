package com.mifareread.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val PLAYLIST_PREFERENCES = "playlist_preferences"
const val SWITCH_THEME_KEY = "key_for_switch_theme"
const val SEARCH_HISTORY_KEY = "key_for_search_history"

class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences =  getSharedPreferences(PLAYLIST_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(SWITCH_THEME_KEY, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(isDarkTheme : Boolean){
        darkTheme = isDarkTheme
        AppCompatDelegate.setDefaultNightMode(
            if(darkTheme){ AppCompatDelegate.MODE_NIGHT_YES}
            else{ AppCompatDelegate.MODE_NIGHT_NO }
        )

        val sharedPreferences =  getSharedPreferences(PLAYLIST_PREFERENCES, MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(SWITCH_THEME_KEY, darkTheme)
            .apply()
    }
}