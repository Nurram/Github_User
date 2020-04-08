package com.nurram.moviecatalogue.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.model.ConstValue
import com.nurram.moviecatalogue.model.Movie
import com.nurram.moviecatalogue.model.Tv
import com.nurram.moviecatalogue.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*

class MovieDetailActivity : AppCompatActivity() {
    private var movie: Movie? = null
    private var tv: Tv? = null
    private var saved = false

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable("movie")
            tv = savedInstanceState.getParcelable("tv")
            saved = savedInstanceState.getBoolean("saved")
        } else {
            movie = intent.getParcelableExtra("movie")
            tv = intent.getParcelableExtra("tv")
            saved = intent.getBooleanExtra("saved", false)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        viewModel.setDb(this)

        if (movie != null) {
            viewModel.getMovies()?.observe(this, Observer {
                checkMovie(it)
            })

            detail_title.text = movie!!.title
            detail_released.append(movie!!.release_date)
            detail_avg_vote.append(movie!!.vote_average.toString())
            detail_overview.text = movie!!.overview

            Glide.with(this).load(ConstValue.BASE_PICTURE_PATH + movie!!.poster_path)
                .centerCrop().into(detail_poster)

            Glide.with(this).load(ConstValue.BASE_PICTURE_PATH + movie!!.backdrop_path)
                .centerCrop().into(detail_backdrop)

            detail_fav.setOnClickListener {
                movie?.let { movie1 ->
                    if (!saved) {
                        viewModel.setMovies(movie1).observe(this, Observer {
                            Toast.makeText(
                                this,
                                this.getString(R.string.movie_saved), Toast.LENGTH_SHORT
                            ).show()
                        })
                    } else {
                        viewModel.deleteMovie(movie1)
                    }
                }
                saved = changeState(saved)
            }

        } else if (tv != null) {
            viewModel.getTv()?.observe(this, Observer {
                checkTv(it)
            })

            detail_title.text = tv!!.name
            detail_released.text = tv!!.first_air_date
            detail_avg_vote.append(tv!!.vote_average.toString())
            detail_overview.text = tv!!.overview

            Glide.with(this).load(ConstValue.BASE_PICTURE_PATH + tv!!.poster_path)
                .centerCrop().into(detail_poster)

            Glide.with(this).load(ConstValue.BASE_PICTURE_PATH + tv!!.backdrop_path)
                .centerCrop().into(detail_backdrop)

            detail_fav.setOnClickListener {
                tv?.let { tv1 ->
                    if (!saved) {
                        viewModel.setTv(tv1).observe(this, Observer {
                            Toast.makeText(
                                this,
                                this.getString(R.string.tv_saved), Toast.LENGTH_SHORT
                            ).show()
                        })
                    } else {
                        viewModel.deleteTv(tv1)
                    }
                }
                saved = changeState(saved)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("movie", movie)
        outState.putParcelable("tv", tv)
        outState.putBoolean("saved", saved)
        super.onSaveInstanceState(outState)
    }

    private fun changeState(saved: Boolean): Boolean {
        if (saved) {
            detail_fav.setImageResource(R.drawable.ic_favorite_border_24dp)
        } else {
            detail_fav.setImageResource(R.drawable.ic_favorite_24dp)
        }

        return !saved
    }

    private fun checkMovie(movies: List<Movie>) {
        for (item: Movie in movies) {
            if (movie?.title == item.title) {
                detail_fav.setImageResource(R.drawable.ic_favorite_24dp)
                saved = true
            }
        }
    }

    private fun checkTv(tvs: List<Tv>) {
        for (item: Tv in tvs) {
            if (tv?.name == item.name) {
                detail_fav.setImageResource(R.drawable.ic_favorite_24dp)
                saved = true
            }
        }
    }
}
