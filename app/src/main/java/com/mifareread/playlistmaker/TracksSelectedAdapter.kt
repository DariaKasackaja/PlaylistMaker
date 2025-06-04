package com.mifareread.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mifareread.playlistmaker.TracksAdapter.TrackClickListener

class TracksSelectedAdapter(private val trackSelectedItemClickListener: TrackSelectedClickListener): RecyclerView.Adapter<TrackViewHolder>() {

    var tracks = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.track_view, parent, false)

        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            trackSelectedItemClickListener.onTrackSelectedClick( tracks[ holder.adapterPosition ] )
        }
    }

    fun interface TrackSelectedClickListener {
        fun onTrackSelectedClick(track: Track)
    }
}