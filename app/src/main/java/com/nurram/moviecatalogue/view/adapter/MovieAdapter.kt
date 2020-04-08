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
import kotlinx.android.synthetic.main.item_list.view.*

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.RecyclerHolder>() {

    private var movie = ArrayList<Movie>()
    private var mOnMovieItemClick: OnMovieItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return RecyclerHolder(view)
    }

    override fun getItemCount(): Int = movie.size
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) =
        holder.movieBind(movie[position])

    fun addMovieData(movie: ArrayList<Movie>) {
        this.movie.clear()
        this.movie.addAll(movie)
        notifyDataSetChanged()
    }

    fun setOnClick(onMovieItemClick: OnMovieItemClick) {
        mOnMovieItemClick = onMovieItemClick
    }

    inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun movieBind(movie: Movie) {
            with(itemView) {
                item_title.text = movie.title
                item_released.append(movie.release_date)
                item_director.append(movie.vote_average.toString())
                item_overview.text = movie.overview
            }

            Glide.with(context).load(ConstValue.BASE_PICTURE_PATH + movie.poster_path)
                .centerCrop().into(itemView.item_poster)

            itemView.setOnClickListener {
                mOnMovieItemClick?.onMovieClick(movie)
            }
        }
    }
}