package com.example.photogallery_lab10;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface PhotoApi {
    @GET("?")
    fun getInterestingPhotos(
            @Query("method") method: String = "flickr.interestingness.getList",
            @Query("api_key") apiKey: String,
            @Query("format") format: String = "json",
            @Query("nojsoncallback") noJsonCallback: Int = 1,
            @Query("per_page") perPage: Int = 100,
            @Query("page") page: Int = 1,
            @Query("extras") extras: String = "url_s"
    ): Call<FlickrResponse>
}
