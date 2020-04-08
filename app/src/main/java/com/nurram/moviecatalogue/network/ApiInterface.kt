package com.nurram.moviecatalogue.network

import com.nurram.moviecatalogue.model.MovieResults
import com.nurram.moviecatalogue.model.TvResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/3/discover/movie?language=en-US")
    fun getMovies(@Query("api_key") key: String): Call<MovieResults>

    @GET("/3/discover/tv?language=en-US")
    fun getTvs(@Query("api_key") key: String): Call<TvResults>
}