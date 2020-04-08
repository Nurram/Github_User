package com.nurram.moviecatalogue.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nurram.moviecatalogue.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getAllMovie(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)
}