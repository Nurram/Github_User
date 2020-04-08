package com.nurram.moviecatalogue.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nurram.moviecatalogue.model.Tv

@Dao
interface TvDao {
    @Query("SELECT * FROM tv_table")
    fun getAllTv(): LiveData<List<Tv>>

    @Insert
    fun insertTv(tv: Tv)

    @Delete
    fun deleteTv(tv: Tv)
}