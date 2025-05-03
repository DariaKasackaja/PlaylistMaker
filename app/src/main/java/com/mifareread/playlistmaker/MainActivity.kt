package com.mifareread.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.search)
        val buttonLibrary = findViewById<Button>(R.id.library)
        val buttonSettings = findViewById<Button>(R.id.settings)

        val buttonClickListener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.
                makeText(this@MainActivity, "Нажата кнопка \"Поиск\"", Toast.LENGTH_SHORT).
                show()
            }
        }

        buttonSearch.setOnClickListener(buttonClickListener)

        buttonLibrary.setOnClickListener{
            Toast.
            makeText(this@MainActivity, "Нажата кнопка \"Медиатека\"", Toast.LENGTH_SHORT).
            show()
        }

        buttonSettings.setOnClickListener {
            Toast.
            makeText(this@MainActivity, "Нажата кнопка \"Настройки\"", Toast.LENGTH_SHORT).
            show()
        }
    }
}