package com.nurram.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurram.githubuser.model.UsersResults
import com.nurram.githubuser.network.ApiCallback
import com.nurram.githubuser.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val retrofitInstance = RetrofitInstance.instance.apiInterface
    private var users = MutableLiveData<UsersResults>()

    fun findUsers(username: String, apiCallback: ApiCallback): LiveData<UsersResults> {
        retrofitInstance.findUsers(username).enqueue(object : Callback<UsersResults> {
            override fun onFailure(call: Call<UsersResults>, t: Throwable) {
                apiCallback.onError(t.message)
            }

            override fun onResponse(call: Call<UsersResults>, response: Response<UsersResults>) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }
        })

        return users
    }
}