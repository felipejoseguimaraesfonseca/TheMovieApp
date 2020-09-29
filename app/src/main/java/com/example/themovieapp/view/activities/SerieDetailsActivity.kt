package com.example.themovieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.themovieapp.R

const val SERIE_BACKDROP = "extra_serie_backdrop"
const val SERIE_POSTER = "extra_serie_poster"
const val SERIE_TITLE = "extra_serie_title"
const val SERIE_RATING = "extra_serie_rating"
const val SERIE_RELEASE_DATE = "extra_serie_release_date"
const val SERIE_OVERVIEW = "extra_serie_overview"

class SerieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_details)

        backdrop = findViewById(R.id.serie_backdrop)
        poster = findViewById(R.id.serie_poster)
        title = findViewById(R.id.serie_title)
        rating = findViewById(R.id.serie_rating)
        releaseDate = findViewById(R.id.serie_release_date)
        overview = findViewById(R.id.serie_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
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
}