package com.example.photogallery_lab10

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoDB)

    @Query("SELECT * FROM favorite_photos")
    suspend fun getAllFavorites(): List<PhotoDB>

    @Query("DELETE FROM favorite_photos WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM favorite_photos")
    suspend fun deleteAll()

    @Query("DELETE FROM favorite_photos WHERE id = :id")
    suspend fun deleteById(id: String)
}