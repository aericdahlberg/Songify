package com.ait.songifyait.api


import com.ait.songifyait.data.*

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SongInformation {
    @GET("tracks/{id}")
    fun getSongInformation(@Header("Authorization") token: String?,
                         @Path("id") id: String?): Call<artistInfo>

    @GET("artists/{id}")
    fun getArtistName(@Header("Authorization") token : String?,
                      @Path("id") id: String?) : Call<artistName>

    @GET("track/{id}")
    fun getSongImage(@Header("Authorization") token: String?,
                        @Path("id") id: String?) : Call<Images>
}

