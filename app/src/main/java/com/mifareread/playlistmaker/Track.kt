package com.mifareread.playlistmaker

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(val trackId: Int,
                 val trackName: String, // Название композиции
                 val artistName: String, // Имя исполнителя
                 val trackTimeMillis: String, // Продолжительность трека
                 val artworkUrl100: String,// Ссылка на изображение обложки
                 val collectionName: String,
                 val releaseDate: String,
                 val primaryGenreName:String,
                 val country:String)  :  Parcelable {

                     constructor(parcel: Parcel)
                             : this(
                         parcel.readInt(),
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "",
                         parcel.readString() ?: "")

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeInt(this.trackId)
        parcel.writeString(this.trackName)
        parcel.writeString(this.artistName)
        parcel.writeString(this.trackTimeMillis)
        parcel.writeString(this.artworkUrl100)
        parcel.writeString(this.collectionName)
        parcel.writeString(this.releaseDate)
        parcel.writeString(this.primaryGenreName)
        parcel.writeString(this.country)
    }

    companion object CREATOR : Parcelable.Creator<Track>{
        override fun createFromParcel( p0: Parcel ): Track {
            return Track(p0)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }

    }

    fun getTimeFormat():String{
        return if(this.trackTimeMillis.isNullOrEmpty()){ "00:00" }
        else{   SimpleDateFormat("mm:ss", Locale.getDefault())
            .format(this.trackTimeMillis.toInt())
        }
    }

}
