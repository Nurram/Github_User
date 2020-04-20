package com.nurram.githubuser.network

import com.nurram.githubuser.model.User
import com.nurram.githubuser.model.UsersResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    fun findUsers(@Query("q") username: String): Call<UsersResults>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UsersResults>

    @GET("users/{username}/followers")
    fun getFollowersUser(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(@Path("username") username: String): Call<List<User>>
}