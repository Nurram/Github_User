package com.nurram.moviecatalogue.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.ConstValue
import com.nurram.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fav_item_list.view.*

class FavMovieAdapter(val context: Context) : RecyclerView.Adapter<FavMovieAdapter.FavHolder>() {
    private var movies = arrayListOf<Movie>()
    private var mOnMovieItemClick: OnMovieItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fav_item_list, parent, false)
        return FavHolder(view)
    }

    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: FavHolder, position: Int) = holder.bind(movies[position])

    fun setOnClick(onMovieItemClick: OnMovieItemClick) {
        mOnMovieItemClick = onMovieItemClick
    }

    fun addMovie(movies: ArrayList<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    inner class FavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                fav_item_title.text = movie.title
                Glide.with(context).load(ConstValue.BASE_PICTURE_PATH + movie.poster_path)
                    .centerCrop().into(fav_item_poster)
            }

            itemView.setOnClickListener {
                mOnMovieItemClick?.onMovieClick(movie)
            }
        }
    }
}