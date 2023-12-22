package com.example.zd3_2up

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("t") title: String,
        @Query("plot") plot: String = "full"
    ): Call<MovieSearch>
}