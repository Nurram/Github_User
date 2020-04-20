package com.nurram.githubuser.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.usersapp.R
import com.nurram.githubuser.model.User
import com.nurram.githubuser.view.adapter.PagerAdapter
import com.nurram.githubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private var user: User? = null
    private var saved = false
    private var menuItem: MenuItem? = null

    private lateinit var viewModel: DetailViewModel
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.elevation = 0f

        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable("user")
            saved = savedInstanceState.getBoolean("saved")
        } else {
            user = intent.getParcelableExtra("user")
            saved = intent.getBooleanExtra("saved", false)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        viewModel.setDb(this)

        val adapter = PagerAdapter(this, supportFragmentManager, user?.login)
        detail_pager.adapter = adapter
        detail_tab.setupWithViewPager(detail_pager)

        if (user != null) {
            viewModel.getUser()?.observe(this, Observer {
                checkUser(it)
            })

            detail_name.text = user?.login

            Glide.with(this).load(user?.avatar_url)
                .centerCrop().into(detail_image)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("user", user)
        outState.putBoolean("saved", saved)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        if (menu != null) {
            this.menu = menu
            menuItem = menu.getItem(1)
        }

        if (saved) {
            menuItem?.setIcon(R.drawable.ic_favorite_24dp)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_fav -> {
                saveDeleteUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveDeleteUser() {
        user?.let { user1 ->
            GlobalScope.launch {
                viewModel.saveDeleteUser(user1, contentResolver, saved)
            }
        }

        saved = changeState(saved)
    }

    private fun changeState(saved: Boolean): Boolean {
        if (saved) {
            menuItem?.setIcon(R.drawable.ic_favorite_border_24dp)
        } else {
            menuItem?.setIcon(R.drawable.ic_favorite_24dp)
            Toast.makeText(baseContext, getString(R.string.user_saved), Toast.LENGTH_SHORT).show()
        }

        return !saved
    }

    private fun checkUser(users: List<User>) {
        for (item: User in users) {
            if (user?.login == item.login) {
                menuItem?.setIcon(R.drawable.ic_favorite_24dp)
                saved = true
            }
        }
    }
}
