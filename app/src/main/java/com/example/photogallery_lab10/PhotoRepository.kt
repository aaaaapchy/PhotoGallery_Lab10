package com.example.photogallery_lab10

class PhotoRepository(private val photoDao: PhotoDao) {
    suspend fun addToFavorites(photo: PhotoDB) {
        photoDao.insertPhoto(photo)
    }

    suspend fun getFavorites(): List<PhotoDB> {
        return photoDao.getAllFavorites()
    }
    suspend fun clearAllFavorites() {
        photoDao.deleteAll()
    }

    suspend fun deleteFavoriteById(id: String) {
        photoDao.deleteById(id)
    }
}