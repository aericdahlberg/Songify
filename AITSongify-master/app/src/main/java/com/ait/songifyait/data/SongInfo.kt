package com.ait.songifyait.data

data class Album(val album_type: String?, val artists: List<artistInfo>?, val available_markets: List<String>?, val external_urls: External_urls?, val href: String?, val id: String?, val images: List<Images>?, val name: String?, val release_date: String?, val release_date_precision: String?, val total_tracks: Number?, val type: String?, val uri: String?)

data class artistInfo(val external_urls: External_urls?, val href: String?, val id: String?, val name: String?, val type: String?, val uri: String?)

data class artistName(val external_urls: External_urls?, val href: String?, val id: String?, val name: String?, val type: String?, val uri: String?)

data class Total(val album: Album?, val artists: List<artistName>?, val available_markets: List<String>?, val disc_number: Number?, val duration_ms: Number?, val explicit: Boolean?, val external_ids: External_ids?, val external_urls: External_urls?, val href: String?, val id: String?, val is_local: Boolean?, val name: String?, val popularity: Number?, val preview_url: String?, val track_number: Number?, val type: String?, val uri: String?)

data class External_ids(val isrc: String?)

data class External_urls(val spotify: String?)

data class Images(val height: Number?, val url: String?, val width: Number?)
