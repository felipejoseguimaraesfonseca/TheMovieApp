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
import com.example.themovieapp.databinding.ActivityMovieDetailsBinding
import com.example.themovieapp.view.adapters.MoviesAdapter
import com.example.themovieapp.viewmodel.FavoritesMoviesModel

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mFavoritesMoviesModel: FavoritesMoviesModel
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mFavoritesMoviesModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(mFavoritesMoviesModel::class.java)

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

        val backdrop = binding.movieBackdrop
        val poster = binding.moviePoster
        val title = binding.movieTitle
        val rating = binding.movieRating
        val releaseDate = binding.movieReleaseDate
        val overview = binding.movieOverview

        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.image_button_like) {
            val viewHolder: RecyclerView.ViewHolder? = null
            val position = viewHolder!!.absoluteAdapterPosition
            val movie = moviesAdapter.differ.currentList[position]
            val mImageButtonLike = binding.imageButtonLike

            if (R.id.image_button_like == R.drawable.ic_like) {
                mImageButtonLike.setImageResource(R.drawable.ic_like_red)
                mFavoritesMoviesModel.saveFavoriteMovie(movie)
            } else if (R.id.image_button_like == R.drawable.ic_like_red) {
                mImageButtonLike.setImageResource(R.drawable.ic_like)
                mFavoritesMoviesModel.deleteFavoriteMovie(movie)
            }
        }
    }

    private fun observe() {
        mFavoritesMoviesModel.messageEventData.observe(this, { stringResId ->
            Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.imageButtonLike.setOnClickListener(this)
    }
}