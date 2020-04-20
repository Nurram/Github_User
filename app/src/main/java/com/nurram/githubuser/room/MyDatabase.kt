package com.nurram.githubuser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nurram.githubuser.model.User

@Database(entities = [User::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

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