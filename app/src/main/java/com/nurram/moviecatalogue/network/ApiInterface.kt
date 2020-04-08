package com.nurram.moviecatalogue.network

import com.nurram.moviecatalogue.model.MovieResults
import com.nurram.moviecatalogue.model.TvResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/discover/movie?language=en-US")
    fun getMovies(@Query("api_key") key: String): Call<MovieResults>

    @GET("/discover/tv?language=en-US")
    fun getTvs(@Query("api_key") key: String): Call<TvResults>

    @GET("/search/movie")
    fun findMovieByTitle(@Query("api_key") key: String,
                         @Query("language")  lang: String,
                         @Query("query") title: String): Call<MovieResults>

    @GET("/search/tv")
    fun findTvByTitle(@Query("api_key") key: String,
                         @Query("language")  lang: String,
                         @Query("query") title: String): Call<TvResults>
}