package com.example.themovieapp.data.model

import com.google.gson.annotations.SerializedName

data class GetSeriesResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val series: List<Serie>,
    @SerializedName("total_pages") val pages: Int
)