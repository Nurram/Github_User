package com.example.usersapp.view

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersapp.MappingHelper
import com.example.usersapp.R
import com.example.usersapp.model.User.Companion.CONTENT_URI
import com.example.usersapp.view.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

       adapter = UserAdapter(this)

        user_recyclerview.adapter = adapter
        user_recyclerview.layoutManager = LinearLayoutManager(this)
        user_recyclerview.setHasFixedSize(true)

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
                adapter.addData(user)
            } else {
                adapter.clear()
                Toast.makeText(this@MainActivity, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
