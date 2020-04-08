package com.nurram.moviecatalogue.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nurram.moviecatalogue.model.Movie
import com.nurram.moviecatalogue.model.Tv

@Database(entities = [Movie::class, Tv::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object {
        private var instance: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase? {
            if (instance == null) {
                synchronized(MyDatabase::class.java) {
                    instance = Room.databaseBuilder(context, MyDatabase::class.java, "myDB")
                        .build()
                }
            }

            return instance
        }

    }
}