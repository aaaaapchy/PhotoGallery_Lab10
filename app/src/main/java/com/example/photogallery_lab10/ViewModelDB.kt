package com.example.photogallery_lab10

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelDB(private val repository: PhotoRepository) : ViewModel() {
    fun addToFavorites(photo: PhotoDB) {
        viewModelScope.launch {
            repository.addToFavorites(photo)
        }
    }
    fun clearAllFavorites() {
        viewModelScope.launch {
            repository.clearAllFavorites()
        }
    }

    fun deleteFavorite(id: String) {
        viewModelScope.launch {
            repository.deleteFavoriteById(id)
        }
    }

    companion object {
        fun factory(repository: PhotoRepository) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ViewModelDB::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ViewModelDB(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}