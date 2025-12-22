package com.example.photogallery_lab10

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_photos")
data class PhotoDB(
    @PrimaryKey val id: String,
    val title: String,
    val url: String
) {

    @Ignore var owner: String = ""
    @Ignore var secret: String = ""
    @Ignore var server: String = ""
    @Ignore var farm: Int = 0
    @Ignore var isPublic: Boolean = true
    @Ignore var isFriend: Boolean = false
    @Ignore var isFamily: Boolean = false
}