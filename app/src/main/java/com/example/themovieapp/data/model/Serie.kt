package com.example.themovieapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "series")
data class Serie(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val rating: Float?,
    @SerializedName("release_date") val releaseDate: String?
) : Serializable