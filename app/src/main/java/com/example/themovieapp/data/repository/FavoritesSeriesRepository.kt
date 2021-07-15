package com.example.themovieapp.data.repository

import android.content.Context
import com.example.themovieapp.data.model.Serie

class FavoritesSeriesRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).favoritesSeriesDao()

    suspend fun saveFavoriteSerie(serie: Serie) = mDatabase.saveFavoriteSerie(serie)

    fun getFavoriteSerie() = mDatabase.getFavoriteSerie()

    suspend fun deleteFavoriteSerie(serie: Serie) = mDatabase.deleteFavoriteSerie(serie)

}