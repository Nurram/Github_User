package com.nurram.githubuser.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurram.githubuser.MappingHelper
import com.nurram.githubuser.model.User
import com.nurram.githubuser.room.MyDatabase

class DetailViewModel : ViewModel() {
    private var db: MyDatabase? = null
    private var processSuccess = MutableLiveData<Boolean>()

    fun setDb(context: Context) {
        db = MyDatabase.getInstance(context)
    }

    fun saveDeleteUser(
        user: User,
        contentResolver: ContentResolver,
        saved: Boolean
    ): LiveData<Boolean> {
        val uriWithId = Uri.parse(User.CONTENT_URI.toString() + "/" + user.id)

        if (!saved) {
            val values = MappingHelper.userToValue(user)
            contentResolver.insert(User.CONTENT_URI, values)
        } else {
            contentResolver.delete(uriWithId, null, null)
        }

        processSuccess.postValue(true)
        return processSuccess
    }

    fun getUser(): LiveData<List<User>>? {
        return db?.userDao()?.getAllUsers()
    }
}