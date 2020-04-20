package com.nurram.githubuser.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.usersapp.R
import com.nurram.githubuser.view.FollowFragment

class PagerAdapter(
    context: Context, manager: FragmentManager, private val user: String?
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val titles = arrayListOf(
        context.getString(R.string.following),
        context.getString(R.string.followers)
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = user?.let { FollowFragment.newInstance("following", it) }
            1 -> fragment = user?.let { FollowFragment.newInstance("followers", it) }
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int = 2
}