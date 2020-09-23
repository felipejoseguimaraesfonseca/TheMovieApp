package com.example.themovieapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.themovieapp.R
import com.example.themovieapp.data.model.Serie

class SeriesAdapter (
    private var series: MutableList<Serie>,
    private var onSerieClick: (serie: Serie) -> Unit
) : RecyclerView.Adapter<SeriesAdapter.SerieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SerieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_serie, parent, false)
        return SerieViewHolder(view)
    }

    override fun getItemCount(): Int = series.size

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.bind(series[position])
    }

    fun appendSeries(series: List<Serie>) {
        this.series.addAll(series)
        notifyItemRangeInserted(
            this.series.size,
            series.size - 1
        )
    }

    inner class SerieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_serie_poster)

        fun bind(serie: Serie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${serie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            itemView.setOnClickListener { onSerieClick.invoke(serie) }
        }
    }
}