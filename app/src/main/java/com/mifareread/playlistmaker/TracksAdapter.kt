package com.mifareread.playlistmaker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TracksAdapter( private val trackItemClickListener: TrackClickListener ):
    RecyclerView.Adapter<TrackViewHolder>() {

        var tracks = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TrackViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent,false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {

            trackItemClickListener.
            onTrackClick( tracks[ holder.adapterPosition ] )
        }
    }

    fun interface TrackClickListener{
        fun onTrackClick( track : Track )
    }
}