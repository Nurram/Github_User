package com.nurram.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.Movie
import com.nurram.moviecatalogue.model.Tv
import com.nurram.moviecatalogue.room.MyDatabase
import com.nurram.moviecatalogue.view.adapter.FavMovieAdapter
import com.nurram.moviecatalogue.view.adapter.FavTvAdapter
import com.nurram.moviecatalogue.view.adapter.OnItemTvClick
import com.nurram.moviecatalogue.view.adapter.OnMovieItemClick
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : Fragment() {
    private var movies = ArrayList<Movie>()
    private var tvs = ArrayList<Tv>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = context?.let { MyDatabase.getInstance(it) }

        fav_movie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val movieAdapter = context?.let { FavMovieAdapter(it) }
        fav_movie.adapter = movieAdapter
        movieAdapter?.setOnClick(object : OnMovieItemClick {
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movie", movie)
                intent.putExtra("saved", true)
                startActivity(intent)
            }

        })

        val movieDao = db?.movieDao()?.getAllMovie()
        movieDao?.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it)
            movieAdapter?.addMovie(movies)
        })

        val tvAdapter = context?.let { FavTvAdapter(it) }
        fav_tv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fav_tv.adapter = tvAdapter
        tvAdapter?.setOnClick(object : OnItemTvClick {
            override fun onTvClick(tv: Tv) {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("tv", tv)
                intent.putExtra("saved", true)
                startActivity(intent)
            }

        })

        val tvDao = db?.tvDao()?.getAllTv()
        tvDao?.observe(viewLifecycleOwner, Observer {
            tvs.clear()
            tvs.addAll(it)
            tvAdapter?.addTv(tvs)
        })
    }
}
