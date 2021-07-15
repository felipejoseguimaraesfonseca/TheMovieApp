package com.example.themovieapp.data.model

import retrofit2.Call
import retrofit2.Response
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

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie")
    suspend fun searchMovies(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8"
    ): Response<GetMoviesResponse>

    @GET("tv/popular")
    fun getPopularSeries(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetSeriesResponse>

    @GET("tv/top_rated")
    fun getTopRatedSeries(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetSeriesResponse>

    @GET("tv/on_the_air")
    fun getOnAirSeries(
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8",
        @Query("page") page: Int
    ): Call<GetSeriesResponse>

    @GET("tv")
    suspend fun searchSeries(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "b8f9a078672898c40eedc8c3006cd8a8"
    ): Response<GetSeriesResponse>
}
