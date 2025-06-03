package com.mifareread.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar


class AudioplayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audioplayer_view)


        val audioBack = findViewById<MaterialToolbar>(R.id.audioBack)

        audioBack.setOnClickListener {  finish()  }
        val cover = findViewById<ImageView>(R.id.audioCoverButton)
        val url = intent.getStringExtra("url")



        Glide.with(applicationContext)
            .load(
                url?.replaceAfterLast('/',
                    "512x512bb.jpg")
            )
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(8))
            .into(cover)
    }
}