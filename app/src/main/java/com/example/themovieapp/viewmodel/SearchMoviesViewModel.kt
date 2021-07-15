package com.example.themovieapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.model.GetMoviesResponse
import com.example.themovieapp.data.repository.MoviesRepository
import com.example.themovieapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchMoviesViewModel(
    application: Application,
    val moviesRepository: MoviesRepository
) : AndroidViewModel(application) {

    val searchMovies: MutableLiveData<Resource<GetMoviesResponse>> = MutableLiveData()
    var searchMoviesPage = 1
    var searchMoviesResponse: GetMoviesResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    fun searchMovies(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        safeSearchMoviesCall(searchQuery)
    }

    private fun handleSearchMoviesResponse(
        response: Response<GetMoviesResponse>
    ) : Resource<GetMoviesResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchMoviesResponse == null || newSearchQuery != oldSearchQuery) {
                    searchMoviesPage = 1
                    oldSearchQuery = newSearchQuery
                    searchMoviesResponse = resultResponse
                } else {
                    searchMoviesPage++
                    val oldMovies = searchMoviesResponse?.movies
                    val newMovies = resultResponse.movies
                    oldMovies?.addAll(newMovies)
                }
                return Resource.Success(searchMoviesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearchMoviesCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchMovies.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = moviesRepository.searchMovies(searchQuery, searchMoviesPage)
                searchMovies.postValue(handleSearchMoviesResponse(response))
            } else {
                searchMovies.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchMovies.postValue(Resource.Error("Network Failure"))
                else -> searchMovies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}