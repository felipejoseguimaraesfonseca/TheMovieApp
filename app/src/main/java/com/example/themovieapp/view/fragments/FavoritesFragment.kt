package com.example.themovieapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.themovieapp.R
import com.example.themovieapp.viewmodel.FavoritesMoviesModel
import com.example.themovieapp.viewmodel.FavoritesSeriesModel

class FavoritesFragment : Fragment() {

    private lateinit var mFavoritesMoviesModel: FavoritesMoviesModel
    private lateinit var mFavoritesSeriesModel: FavoritesSeriesModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavoritesMoviesModel = ViewModelProvider(this).get(FavoritesMoviesModel::class.java)
        mFavoritesSeriesModel = ViewModelProvider(this).get(FavoritesSeriesModel::class.java)

        getFavorites()
    }

    private fun getFavorites() {
        mFavoritesMoviesModel.getFavoriteMovie()
        mFavoritesSeriesModel.getFavoriteSerie()
    }
}