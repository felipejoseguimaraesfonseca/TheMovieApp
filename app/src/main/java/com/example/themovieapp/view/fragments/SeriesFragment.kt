package com.example.themovieapp.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.R
import com.example.themovieapp.data.model.Serie
import com.example.themovieapp.data.repository.SeriesRepository
import com.example.themovieapp.view.activities.*
import com.example.themovieapp.view.adapters.SeriesAdapter

class SeriesFragment : Fragment() {

    private lateinit var popularSeries: RecyclerView
    private lateinit var popularSeriesAdapter: SeriesAdapter
    private lateinit var popularSeriesLayoutMgr: LinearLayoutManager

    private var popularSeriesPage = 1

    private lateinit var topRatedSeries: RecyclerView
    private lateinit var topRatedSeriesAdapter: SeriesAdapter
    private lateinit var topRatedSeriesLayoutMgr: LinearLayoutManager

    private var topRatedSeriesPage = 1

    private lateinit var onAirSeries: RecyclerView
    private lateinit var onAirSeriesAdapter: SeriesAdapter
    private lateinit var onAirSeriesLayoutMgr: LinearLayoutManager

    private var onAirSeriesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularSeries = view.findViewById(R.id.popular_series)
        popularSeriesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularSeries.layoutManager = popularSeriesLayoutMgr
        popularSeriesAdapter =
            SeriesAdapter(mutableListOf()) { serie ->
                showSerieDetails(serie)
            }
        popularSeries.adapter = popularSeriesAdapter

        topRatedSeries = view.findViewById(R.id.top_rated_series)
        topRatedSeriesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedSeries.layoutManager = topRatedSeriesLayoutMgr
        topRatedSeriesAdapter =
            SeriesAdapter(mutableListOf()) { serie ->
                showSerieDetails(serie)
            }
        topRatedSeries.adapter = topRatedSeriesAdapter

        onAirSeries = view.findViewById(R.id.series_on_tv)
        onAirSeriesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        onAirSeries.layoutManager = onAirSeriesLayoutMgr
        onAirSeriesAdapter =
            SeriesAdapter(mutableListOf()) { serie ->
                showSerieDetails(serie)
            }
        onAirSeries.adapter = onAirSeriesAdapter

        getPopularSeries()
        getTopRatedSeries()
        getOnAirSeries()
    }

    private fun getPopularSeries() {
        SeriesRepository.getPopularSeries(
            popularSeriesPage,
            ::onPopularSeriesFetched,
            ::onError
        )
    }

    private fun onPopularSeriesFetched(series: List<Serie>) {
        popularSeriesAdapter.appendSeries(series)
        attachPopularSeriesOnScrollListener()
    }

    private fun attachPopularSeriesOnScrollListener() {
        popularSeries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularSeriesLayoutMgr.itemCount
                val visibleItemCount = popularSeriesLayoutMgr.childCount
                val firstVisibleItem = popularSeriesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularSeries.removeOnScrollListener(this)
                    popularSeriesPage++
                    getPopularSeries()
                }
            }
        })
    }

    private fun getTopRatedSeries() {
        SeriesRepository.getTopRatedSeries(
            topRatedSeriesPage,
            ::onTopRatedSeriesFetched,
            ::onError
        )
    }

    private fun onTopRatedSeriesFetched(series: List<Serie>) {
        topRatedSeriesAdapter.appendSeries(series)
        attachTopRatedSeriesOnScrollListener()
    }

    private fun attachTopRatedSeriesOnScrollListener() {
        topRatedSeries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedSeriesLayoutMgr.itemCount
                val visibleItemCount = topRatedSeriesLayoutMgr.childCount
                val firstVisibleItem = topRatedSeriesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedSeries.removeOnScrollListener(this)
                    topRatedSeriesPage++
                    getTopRatedSeries()
                }
            }
        })
    }

    private fun getOnAirSeries() {
        SeriesRepository.getOnAirSeries(
            onAirSeriesPage,
            ::onAirSeriesFetched,
            ::onError
        )
    }

    private fun onAirSeriesFetched(series: List<Serie>) {
        onAirSeriesAdapter.appendSeries(series)
        attachOnAirSeriesOnScrollListener()
    }

    private fun attachOnAirSeriesOnScrollListener() {
        onAirSeries.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = onAirSeriesLayoutMgr.itemCount
                val visibleItemCount = onAirSeriesLayoutMgr.childCount
                val firstVisibleItem = onAirSeriesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    onAirSeries.removeOnScrollListener(this)
                    onAirSeriesPage++
                    getOnAirSeries()
                }
            }
        })
    }

    private fun showSerieDetails(serie: Serie) {
        val intent = Intent(this.context, SerieDetailsActivity::class.java)
        intent.putExtra(SERIE_BACKDROP, serie.backdropPath)
        intent.putExtra(SERIE_POSTER, serie.posterPath)
        intent.putExtra(SERIE_TITLE, serie.title)
        intent.putExtra(SERIE_RATING, serie.rating)
        intent.putExtra(SERIE_RELEASE_DATE, serie.releaseDate)
        intent.putExtra(SERIE_OVERVIEW, serie.overview)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(this.context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}