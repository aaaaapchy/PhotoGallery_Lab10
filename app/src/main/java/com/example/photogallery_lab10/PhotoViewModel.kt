package com.example.photogallery_lab10

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PhotoViewModel : ViewModel(){
    val photos = mutableStateListOf<Photo>()
    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")  // Убедись, что здесь правильный URL!
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val api = retrofit.create(PhotoApi::class.java)

        api.getInterestingPhotos(
            apiKey = "5c1a5951a8a17c676482dea0f679850e"
        ).enqueue(object : Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("Flickr", "Ответ успешен! stat = ${body?.stat}")
                    val photoList = body?.photos?.photo
                    Log.d("Flickr", "Получено фото: ${photoList?.size ?: 0}")

                    if (photoList != null) {
                        photos.clear()
                        photos.addAll(photoList)
                        Log.d("Flickr", "Список обновлён, теперь size = ${photos.size}")
                    }
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