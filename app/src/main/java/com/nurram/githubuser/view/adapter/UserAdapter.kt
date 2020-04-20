package com.nurram.githubuser.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usersapp.R
import com.nurram.githubuser.model.User
import kotlinx.android.synthetic.main.fav_item_list.view.*

class UserAdapter(val context: Context?) : RecyclerView.Adapter<UserAdapter.FavHolder>() {
    private var users = arrayListOf<User>()
    private var onItemClick: OnUserClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fav_item_list, parent, false)
        return FavHolder(view)
    }

    override fun getItemCount(): Int = users.size
    override fun onBindViewHolder(holder: FavHolder, position: Int) = holder.bind(users[position])

    fun setOnClick(onMovieItemClick: OnUserClick) {
        onItemClick = onMovieItemClick
    }

    fun addUser(users: ArrayList<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    inner class FavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                fav_item_username.text = user.login
                Glide.with(context).load(user.avatar_url)
                    .centerCrop().into(fav_item_image)
            }

            itemView.setOnClickListener {
                onItemClick?.onMovieClick(user)
            }
        }
    }
}