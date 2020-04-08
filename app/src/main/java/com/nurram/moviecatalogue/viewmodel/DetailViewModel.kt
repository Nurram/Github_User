package com.nurram.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurram.moviecatalogue.model.Movie
import com.nurram.moviecatalogue.model.Tv
import com.nurram.moviecatalogue.room.MyDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private var db: MyDatabase? = null
    private var processSuccess = MutableLiveData<Boolean>()

    fun setDb(context: Context) {
        db = MyDatabase.getInstance(context)
    }

    fun setMovies(movie: Movie): LiveData<Boolean> {
        GlobalScope.launch {
            db?.movieDao()?.insertMovie(movie)
        }

        processSuccess.postValue(true)
        return processSuccess
    }

    fun getMovies(): LiveData<List<Movie>>? {
        return db?.movieDao()?.getAllMovie()
    }

    fun deleteMovie(movie: Movie) {
        GlobalScope.launch {
            db?.movieDao()?.deleteMovie(movie)
        }
    }

    fun setTv(tv: Tv): LiveData<Boolean> {
        GlobalScope.launch {
            db?.tvDao()?.insertTv(tv)
        }

        processSuccess.postValue(true)
        return processSuccess
    }

    fun getTv(): LiveData<List<Tv>>? {
        return db?.tvDao()?.getAllTv()
    }

    fun deleteTv(tv: Tv) {
        GlobalScope.launch {
            db?.tvDao()?.deleteTv(tv)
        }
    }
}