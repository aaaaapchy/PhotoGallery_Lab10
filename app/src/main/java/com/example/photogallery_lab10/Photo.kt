package com.example.photogallery_lab10

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass




@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "owner")
    val owner: String = "",
    @Json(name = "secret")
    val secret: String = "",
    @Json(name = "server")
    val server: String = "",
    @Json(name = "farm")
    val farm: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "ispublic")
    val isPublic: Int = 0,
    @Json(name = "isfriend")
    val isFriend: Int = 0,
    @Json(name = "isfamily")
    val isFamily: Int = 0
){
    fun imageUrl(): String =
        "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}.jpg"
}

@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "photo")
    val photo: List<Photo> = emptyList()
)

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    @Json(name = "photos")
    val photos: Photos,
    @Json(name = "stat") val stat: String = ""
)
