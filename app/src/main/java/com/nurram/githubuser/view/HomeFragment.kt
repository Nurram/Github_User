package com.nurram.githubuser.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersapp.R
import com.nurram.githubuser.model.User
import com.nurram.githubuser.network.ApiCallback
import com.nurram.githubuser.view.adapter.OnUserClick
import com.nurram.githubuser.view.adapter.UserAdapter
import com.nurram.githubuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ApiCallback {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )

        adapter = UserAdapter(context)
        adapter.setOnClick(object : OnUserClick {
            override fun onMovieClick(user: User) {
                val intent = Intent(context, UserDetailActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }

        })

        user_recyclerview.adapter = adapter
        user_recyclerview.layoutManager = LinearLayoutManager(context)
        user_recyclerview.setHasFixedSize(true)

        search_box.isIconified = false
        search_box.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                user_progress.visibility = View.VISIBLE
                getData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    fun getData(query: String) {
        viewModel.findUsers(query, this).observe(this, Observer {
            user_progress.visibility = View.GONE

            if (it.items.size > 0) {
                adapter.addUser(it.items)
            } else {
                Toast.makeText(context, getString(R.string.no_result), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onError(t: String?) {
        Toast.makeText(context, t, Toast.LENGTH_SHORT).show()
    }
}
