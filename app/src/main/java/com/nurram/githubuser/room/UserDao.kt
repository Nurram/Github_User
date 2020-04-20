package com.nurram.githubuser.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.nurram.githubuser.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUsersToCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User): Int

    @Query("DELETE FROM user_table WHERE id=:id")
    fun deleteById(id: Int): Int
}