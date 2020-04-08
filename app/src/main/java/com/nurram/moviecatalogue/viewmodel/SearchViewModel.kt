package com.nurram.moviecatalogue.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurram.moviecatalogue.model.ConstValue
import com.nurram.moviecatalogue.model.MovieResults
import com.nurram.moviecatalogue.model.TvResults
import com.nurram.moviecatalogue.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private val retrofitInstance = RetrofitInstance.instance.apiInterface
    private val movieResults = MutableLiveData<MovieResults>()
    private val tvResults = MutableLiveData<TvResults>()

    fun findMovieByTitles(title: String): LiveData<MovieResults> {
        val formatedTitle = title.replace(" ", "-")
        retrofitInstance.findMovieByTitle(ConstValue.API_KEY, ConstValue.LANGUAGE, formatedTitle)
            .enqueue(object : Callback<MovieResults>{
                override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                    if (response.isSuccessful) movieResults.postValue(response.body())
                }

                override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    Log.d("TAG", t.localizedMessage)
                }

            })

        return movieResults
    }

    fun findTvByTitle(title: String): LiveData<TvResults> {
        val formatedTitle = title.replace(" ", "-")
        retrofitInstance.findTvByTitle(ConstValue.API_KEY, ConstValue.LANGUAGE, formatedTitle)
            .enqueue(object: Callback<TvResults> {
                override fun onFailure(call: Call<TvResults>, t: Throwable) {
                    Log.d("TAG", t.localizedMessage)
                }

                override fun onResponse(call: Call<TvResults>, response: Response<TvResults>) {
                    if (response.isSuccessful) tvResults.postValue(response.body())
                }

            })

        return tvResults
    }
}