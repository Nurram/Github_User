package com.nurram.githubuser.view

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersapp.R
import com.nurram.githubuser.MappingHelper
import com.nurram.githubuser.model.User
import com.nurram.githubuser.model.User.Companion.CONTENT_URI
import com.nurram.githubuser.view.adapter.OnUserClick
import com.nurram.githubuser.view.adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_favourite)

        fav_movie.layoutManager =
            LinearLayoutManager(this)
        userAdapter = UserAdapter(this)
        fav_movie.adapter = userAdapter
        userAdapter.setOnClick(object : OnUserClick {
            override fun onMovieClick(user: User) {
                val intent = Intent(this@FavouriteActivity, UserDetailActivity::class.java)
                intent.putExtra("user", user)
                intent.putExtra("saved", true)
                startActivity(intent)
            }

        })

        getData()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                getData()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun getData() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val user = deferredNotes.await()
            if (user.size > 0) {
                userAdapter.addUser(user)
            } else {
                userAdapter.clear()
                Toast.makeText(this@FavouriteActivity, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
