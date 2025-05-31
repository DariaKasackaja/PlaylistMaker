package com.mifareread.playlistmaker

import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    private val cover:ImageView = itemView.findViewById(R.id.cover)
    private val trackTittle:TextView = itemView.findViewById(R.id.track_title)
    private val trackArtist:TextView = itemView.findViewById(R.id.track_artist)
    private val trackTime:TextView = itemView.findViewById(R.id.track_time)

    fun bind( track : Track ){
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(2.0f, itemView)))
                .into(cover)

        trackTittle.text = track.trackName
        val time = if(track.trackTimeMillis.isNullOrEmpty()){
            ""
        }
        else{   SimpleDateFormat("mm:ss", Locale.getDefault())
                        .format(track.trackTimeMillis.toInt())
        }

        trackTime.text =  time
        trackArtist.text = track.artistName

    }

    private fun dpToPx(dp: Float, context: View): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}