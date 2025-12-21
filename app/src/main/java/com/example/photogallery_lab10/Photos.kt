package com.example.photogallery_lab10

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "photo")
    val photo: List<Photo> = emptyList()
)


