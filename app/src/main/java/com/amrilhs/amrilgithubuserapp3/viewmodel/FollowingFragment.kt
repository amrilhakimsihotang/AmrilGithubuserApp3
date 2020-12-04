package com.amrilhs.amrilgithubuserapp3.viewmodel


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.adapter.FollowingAdapter
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.amrilhs.amrilgithubuserapp3.model.UsersFollowing
import kotlinx.android.synthetic.main.following_fragment.*

class FollowingFragment : Fragment() {
    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var listFollowing: ArrayList<UsersFollowing> = ArrayList()
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingAdapter = FollowingAdapter(listFollowing)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
           FollowingViewModel::class.java
        )

        val userData = requireActivity().intent.getParcelableExtra<UsersData>(EXTRA_DETAIL) as UsersData
        recyclerviewFollowing()
        followingViewModel.getDataGit(requireActivity().applicationContext,userData.username.toString())

        showprogressBarFollowing(true)

        followingViewModel.getAllFollowing().observe(requireActivity(), { listFollowing ->
            if (listFollowing != null) {
                followingAdapter.setFollowing(listFollowing)
                showprogressBarFollowing(false)
            }
        })
        recyclerviewFollowing()
    }


    private fun recyclerviewFollowing() {
        recyclerView_Following.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView_Following.setHasFixedSize(true)
        recyclerView_Following.adapter = followingAdapter
    }

    private fun showprogressBarFollowing(state: Boolean) {
        if (state) {
            progressBarFollowing.visibility = View.VISIBLE
        } else {
            progressBarFollowing.visibility = View.INVISIBLE
        }
    }
}