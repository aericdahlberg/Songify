package com.ait.songifyait.api

import com.ait.songifyait.data.AudioFeatures
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

//Example path:
//https://api.spotify.com/v1/audio-analysis/11dFghVXANMlKmJXsNCbNl
//sample ID: 098ttCNmncrO4YvqWUNMvn
//sample token: Bearer BQBOtFvXlFqPFM_HRNc3eFVEduRjGK9fjN0bKJ194NslmpRjTiQv3-B2rQGjc-gxnDjenheBSH0F3HGVBNM

interface TrackFeatures {
    @GET("audio-features/{id}")
    fun getTrackFeatures(@Header("Authorization") token: String?,
                         @Path("id") id: String?): Call<AudioFeatures>

}