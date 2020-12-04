package com.amrilhs.amrilgithubuserapp3.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.viewmodel.FollowerFragment
import com.amrilhs.amrilgithubuserapp3.viewmodel.FollowingFragment

class ViewPagerAdapter (private val context: Context, fm: FragmentManager):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val pages= listOf(
        FollowingFragment(),
        FollowerFragment()
    )
    private val tabTitles = intArrayOf(
        R.string.following_vp,
        R.string.follower_vp
    )
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }
    override fun getCount(): Int=2

    override fun getItem(position: Int): Fragment {
        return  pages[position]

    }
}