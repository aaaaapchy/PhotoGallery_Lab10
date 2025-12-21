package com.example.photogallery_lab10

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    @Json(name = "photos")
    val photos: Photos,
    @Json(name = "stat") val stat: String = ""
)
