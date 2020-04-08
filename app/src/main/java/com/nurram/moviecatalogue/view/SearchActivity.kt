package com.nurram.moviecatalogue.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.Movie
import com.nurram.moviecatalogue.model.Tv
import com.nurram.moviecatalogue.view.adapter.MovieAdapter
import com.nurram.moviecatalogue.view.adapter.OnItemTvClick
import com.nurram.moviecatalogue.view.adapter.OnMovieItemClick
import com.nurram.moviecatalogue.view.adapter.TvAdapter
import com.nurram.moviecatalogue.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: SearchViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvAdapter: TvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(SearchViewModel::class.java)

        movieAdapter = MovieAdapter(this)
        movieAdapter.setOnClick(object : OnMovieItemClick{
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(this@SearchActivity, MovieDetailActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }

        })

        search_movie.adapter = movieAdapter
        search_movie.layoutManager = LinearLayoutManager(this)

        tvAdapter = TvAdapter(this)
        tvAdapter.setOnClick(object: OnItemTvClick{
            override fun onTvClick(tv: Tv) {
                val intent = Intent(this@SearchActivity, MovieDetailActivity::class.java)
                intent.putExtra("tv", tv)
                startActivity(intent)
            }

        })

        search_tv.adapter = tvAdapter
        search_tv.layoutManager = LinearLayoutManager(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.findMovieByTitles(query).observe(this, Observer {
                if (it.results.size > 0) {
                    search_movie_tv.visibility = View.VISIBLE
                    search_movie.visibility = View.VISIBLE
                    search_progress.visibility = View.GONE
                    movieAdapter.addMovieData(it.results)
                } else {
                    search_progress.visibility = View.GONE
                }
            })

            viewModel.findTvByTitle(query).observe(this, Observer {
                if (it.results.size > 0) {
                    search_tvs_tv.visibility = View.VISIBLE
                    search_tv.visibility = View.VISIBLE
                    search_progress.visibility = View.GONE
                    tvAdapter.addTvData(it.results)
                } else {
                    search_progress.visibility = View.GONE
                }
            })
        } else {
            return false
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}
