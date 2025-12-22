package com.example.photogallery_lab10

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PhotoViewModel : ViewModel() {

    val photos = mutableStateListOf<Photo>()

    init {
        loadPhotos()
    }


    fun searchPhotos(query: String?) {
        loadPhotos(query?.trim())
    }


    private fun loadPhotos(query: String? = null) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val api = retrofit.create(PhotoApi::class.java)

        val call: Call<FlickrResponse> = if (query.isNullOrBlank()) {
            // список фотографий
            api.getInterestingPhotos(
                apiKey = "8a55a5f1e39fd8bcb84055b3775b7496",
                perPage = 100
            )
        } else {
            // поиск по слову
            api.searchPhotos(
                apiKey = "8a55a5f1e39fd8bcb84055b3775b7496",
                text = query,
                perPage = 100
            )
        }

        call.enqueue(object : Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Flickr", "Ответ успешен! stat = ${body?.stat}")

                    val photoList = body?.photos?.photo ?: emptyList()

                    Log.d("Flickr", "Получено фото: ${photoList.size}")

                    photos.clear()
                    photos.addAll(photoList)

                    Log.d("Flickr", "Список обновлён, теперь size = ${photos.size}")
                } else {
                    Log.e("Flickr", "Ошибка HTTP: ${response.code()}")
                    Log.e("Flickr", "Тело ошибки: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e("Flickr", "Запрос провалился", t)
            }
        })
    }
}