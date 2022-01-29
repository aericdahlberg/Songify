package com.ait.songifyait.data

data class AudioFeatures(val artist: String?, val acousticness: Number?, val analysis_url: String?, val danceability: Number?, val duration_ms: Number?, val energy: Number?, val id: String?, val instrumentalness: Number?, val key: Number?, val liveness: Number?, val loudness: Number?, val mode: Number?, val speechiness: Number?, val tempo: Number?, val time_signature: Number?, val track_href: String?, val type: String?, val uri: String?, val valence: Number?)

data class Base(val audio_features: List<AudioFeatures>?)