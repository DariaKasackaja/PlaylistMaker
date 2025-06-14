package com.mifareread.playlistmaker


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("/search")
    fun search(@Query("term") text: String): Call<TracksResponse>

}