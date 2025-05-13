package com.mifareread.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<Button>(R.id.back_button)
        buttonBack.setOnClickListener { finish() }

        val buttonShare=findViewById<TextView>(R.id.share_button)
        buttonShare.setOnClickListener {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.setType("text/plain")
            intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            startActivity(Intent.createChooser(intentShare, getString(R.string.share_question) ))

        }

        val buttonSupport = findViewById<TextView>(R.id.support_button)
        buttonSupport.setOnClickListener {
            val intentSupport = Intent(Intent.ACTION_SENDTO)
            intentSupport.data = Uri.parse("mailto:")
            intentSupport.putExtra(Intent.EXTRA_EMAIL, getString(R.string.mail))
            intentSupport.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_name))
            intentSupport.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_text))
            startActivity(intentSupport)
        }

        val buttonForward = findViewById<TextView>(R.id.forward_button)
        buttonForward.setOnClickListener {
            val intentForward = Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.forward_url)) )
            startActivity(intentForward)
        }
    }
}