package com.nurram.moviecatalogue.view.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nurram.moviecatalogue.R
import com.nurram.moviecatalogue.view.FavouriteFragment
import com.nurram.moviecatalogue.view.MovieFragment
import com.nurram.moviecatalogue.view.TvShowFragment

class PagerAdapter(private val context: Context, manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TITLES = intArrayOf(
        R.string.movies,
        R.string.tv_shows,
        R.string.favourites
    )
    private val mFragments = arrayOf(
        MovieFragment(),
        TvShowFragment(),
        FavouriteFragment()
    )

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TITLES[position])
    }
}