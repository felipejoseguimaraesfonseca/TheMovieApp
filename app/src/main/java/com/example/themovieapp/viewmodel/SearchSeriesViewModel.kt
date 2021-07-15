package com.example.themovieapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.model.GetSeriesResponse
import com.example.themovieapp.data.repository.SeriesRepository
import com.example.themovieapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchSeriesViewModel(
    application: Application,
    val seriesRepository: SeriesRepository
) : AndroidViewModel(application) {

    val searchSeries: MutableLiveData<Resource<GetSeriesResponse>> = MutableLiveData()
    var searchSeriesPage = 1
    var searchSeriesResponse: GetSeriesResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    fun searchSeries(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        safeSearchSeriesCall(searchQuery)
    }

    private fun handleSearchSeriesResponse(
        response: Response<GetSeriesResponse>
    ) : Resource<GetSeriesResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchSeriesResponse == null || newSearchQuery != oldSearchQuery) {
                    searchSeriesPage = 1
                    oldSearchQuery = newSearchQuery
                    searchSeriesResponse = resultResponse
                } else {
                    searchSeriesPage++
                    val oldSeries = searchSeriesResponse?.series
                    val newSeries = resultResponse.series
                    oldSeries?.addAll(newSeries)
                }
                return Resource.Success(searchSeriesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearchSeriesCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchSeries.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = seriesRepository.searchSeries(searchQuery, searchSeriesPage)
                searchSeries.postValue(handleSearchSeriesResponse(response))
            } else {
                searchSeries.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchSeries.postValue(Resource.Error("Network Failure"))
                else -> searchSeries.postValue(Resource.Error("Conversion Error"))
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
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}