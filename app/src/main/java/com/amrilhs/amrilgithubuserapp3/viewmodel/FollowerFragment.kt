package com.amrilhs.amrilgithubuserapp3.viewmodel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.adapter.FollowerAdapter
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.amrilhs.amrilgithubuserapp3.model.UsersFollower
import kotlinx.android.synthetic.main.follower_fragment.*

class FollowerFragment : Fragment() {
    companion object {
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var listFollower: ArrayList<UsersFollower>  = ArrayList()
    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.follower_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerAdapter = FollowerAdapter(listFollower)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)


        val usersFollower = requireActivity().intent.getParcelableExtra<UsersData>(EXTRA_DETAIL)
        recyclerviewFollower()

        followerViewModel.getDataGit(requireActivity().applicationContext,usersFollower!!.username.toString())
        showprogressBarFollower(true)

        followerViewModel.getAllFollower().observe(requireActivity(), { listFollower ->
            if (listFollower != null) {
                followerAdapter.setFollower(listFollower)
                showprogressBarFollower(false)
            }
        })
    }

    private fun recyclerviewFollower() {
        recyclerView_Followers.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView_Followers.setHasFixedSize(true)
        recyclerView_Followers.adapter= followerAdapter
    }

    private fun showprogressBarFollower(state: Boolean) {
        if (state) {
            progressBarFollowers.visibility = View.VISIBLE
        } else {
            progressBarFollowers.visibility = View.INVISIBLE
        }
    }

}