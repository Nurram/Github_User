package com.nurram.moviecatalogue.view.adapter

import com.nurram.moviecatalogue.model.Movie

interface OnMovieItemClick {
    fun onMovieClick(movie: Movie)
}