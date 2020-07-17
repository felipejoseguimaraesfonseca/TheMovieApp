package com.example.themovieapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}
