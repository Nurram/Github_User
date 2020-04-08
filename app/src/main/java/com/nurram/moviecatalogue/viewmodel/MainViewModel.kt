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

class MainViewModel : ViewModel() {
    private val retrofitInstance = RetrofitInstance.instance.apiInterface
    private var movieList = MutableLiveData<MovieResults>()
    private var tvList = MutableLiveData<TvResults>()

    fun getMovies(): LiveData<MovieResults> {
        retrofitInstance.getMovies(ConstValue.API_KEY).enqueue(object : Callback<MovieResults> {
            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                Log.d("TAG", t.message)
            }

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                }
            }
        })

        return movieList
    }

    fun getTvs(): LiveData<TvResults> {
        retrofitInstance.getTvs(ConstValue.API_KEY).enqueue(object : Callback<TvResults> {
            override fun onFailure(call: Call<TvResults>, t: Throwable) {
                Log.d("TAG", t.message)
            }

            override fun onResponse(call: Call<TvResults>, response: Response<TvResults>) {
                if (response.isSuccessful) {
                    tvList.postValue(response.body())
                }
            }
        })

        return tvList
    }
}