package com.example.photogallery_lab10;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface PhotoApi {

    @GET("services/rest/")
    Call<FlickrResponse> getInterestingPhotos(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") int noJsonCallback
    );
}
