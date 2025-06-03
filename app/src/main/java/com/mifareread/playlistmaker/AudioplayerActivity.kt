package com.mifareread.playlistmaker

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.MaterialToolbar



class AudioplayerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audioplayer_view)


        val audioBack = findViewById<MaterialToolbar>(R.id.audioBack)
        val cover = findViewById<ImageView>(R.id.audioCoverButton)
        val timeValueTextView = findViewById<TextView>(R.id.timeValue)
        val albumValueTextView = findViewById<TextView>(R.id.albumValue)
        val yearValueTextView = findViewById<TextView>(R.id.yearValue)
        val genreValueTextView = findViewById<TextView>(R.id.genreValue)
        val countryValueTextView = findViewById<TextView>(R.id.countryValue)
        val trackTitle = findViewById<TextView>(R.id.audioTrackTittle)
        val artistsName = findViewById<TextView>(R.id.audioArtistName)

        val track: Track? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("track", Track::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("track")
        }

        if( track != null ) {
            Glide.with(applicationContext)
                .load(
                    track.artworkUrl100.replaceAfterLast(
                        '/',
                        "512x512bb.jpg"
                    )
                )
                .placeholder(R.drawable.placeholder_big)
                .centerCrop()
                .transform(RoundedCorners(8))
                .into(cover)

            albumValueTextView.text = track.trackName
            countryValueTextView.text = track.country
            yearValueTextView.text = track.getYear()
            genreValueTextView.text = track.primaryGenreName

            timeValueTextView.text = track.getTimeFormat()

            artistsName.text = track.artistName
            trackTitle.text = track.trackName
        }

        audioBack.setOnClickListener {  finish()  }

    }
}