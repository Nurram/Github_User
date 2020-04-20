package com.nurram.githubuser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersapp.R
import com.nurram.githubuser.model.User
import com.nurram.githubuser.view.adapter.UserAdapter
import com.nurram.githubuser.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_following.*


/**
 * A simple [Fragment] subclass.
 */
class FollowFragment private constructor() : Fragment() {
    private lateinit var viewModel: FollowViewModel

    companion object {
        private const val ARGS_FOLLOW_FRAGMENT_KEY = "args_follow_fragment"
        private const val ARGS_FOLLOW_FRAGMENT_USER = "args_follow_user"

        fun newInstance(key: String, user: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_FOLLOW_FRAGMENT_KEY, key)
            bundle.putString(ARGS_FOLLOW_FRAGMENT_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var key = ""
        var user = ""
        if (getArguments() != null) {
            key = arguments?.getString(ARGS_FOLLOW_FRAGMENT_KEY) as String
            user = arguments?.getString(ARGS_FOLLOW_FRAGMENT_USER) as String
        }

        val adapter = context?.let { UserAdapter(it) }
        follow_recycle.adapter = adapter
        follow_recycle.layoutManager = LinearLayoutManager(context)
        follow_recycle.setHasFixedSize(true)

        if (key == "following") {
            viewModel.getFollowingUser(user).observe(viewLifecycleOwner, Observer {
                adapter?.addUser(it as ArrayList<User>)
            })
        } else {
            viewModel.getFollowersUser(user).observe(viewLifecycleOwner, Observer {
                adapter?.addUser(it as ArrayList<User>)
            })
        }
    }
}
