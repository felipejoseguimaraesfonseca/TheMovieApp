package com.example.themovieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.themovieapp.R
import com.example.themovieapp.databinding.ActivitySerieDetailsBinding
import com.example.themovieapp.view.adapters.SeriesAdapter
import com.example.themovieapp.viewmodel.FavoritesSeriesModel

const val SERIE_BACKDROP = "extra_serie_backdrop"
const val SERIE_POSTER = "extra_serie_poster"
const val SERIE_TITLE = "extra_serie_title"
const val SERIE_RATING = "extra_serie_rating"
const val SERIE_RELEASE_DATE = "extra_serie_release_date"
const val SERIE_OVERVIEW = "extra_serie_overview"

class SerieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mFavoritesSeriesModel: FavoritesSeriesModel
    private lateinit var binding: ActivitySerieDetailsBinding
    private lateinit var seriesAdapter: SeriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySerieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mFavoritesSeriesModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(mFavoritesSeriesModel::class.java)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        observe()

        setListeners()
    }

    private fun populateDetails(extras: Bundle) {

        val backdrop = binding.serieBackdrop
        val poster = binding.seriePoster
        val title = binding.serieTitle
        val rating = binding.serieRating
        val releaseDate = binding.serieReleaseDate
        val overview = binding.serieOverview

        extras.getString(SERIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(SERIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(SERIE_TITLE, "")
        rating.rating = extras.getFloat(SERIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(SERIE_RELEASE_DATE, "")
        overview.text = extras.getString(SERIE_OVERVIEW, "")
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.image_button_like) {
            val viewHolder: RecyclerView.ViewHolder? = null
            val position = viewHolder!!.absoluteAdapterPosition
            val serie = seriesAdapter.differ.currentList[position]
            val mImageButtonLike = binding.imageButtonLike

            if (R.id.image_button_like == R.drawable.ic_like) {
                mImageButtonLike.setImageResource(R.drawable.ic_like_red)
                mFavoritesSeriesModel.saveFavoriteSerie(serie)
            } else if (R.id.image_button_like == R.drawable.ic_like_red) {
                mImageButtonLike.setImageResource(R.drawable.ic_like)
                mFavoritesSeriesModel.deleteFavoriteSerie(serie)
            }
        }
    }

    private fun observe() {
        mFavoritesSeriesModel.messageEventData.observe(this, { stringResId ->
            Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.imageButtonLike.setOnClickListener(this)
    }

}