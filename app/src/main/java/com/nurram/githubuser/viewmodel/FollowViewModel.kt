package com.nurram.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurram.githubuser.model.User
import com.nurram.githubuser.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val retrofitInstance = RetrofitInstance.instance
    private var users = MutableLiveData<List<User>>()

    fun getFollowingUser(user: String): LiveData<List<User>> {
        retrofitInstance.apiInterface.getFollowingUser(user).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {}

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }
        })

        return users
    }

    fun getFollowersUser(user: String): LiveData<List<User>> {
        retrofitInstance.apiInterface.getFollowersUser(user).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {}

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }
        })

        return users
    }
}