package com.amrilhs.amrilgithubuserapp3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.adapter.UsersDataAdapter
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.amrilhs.amrilgithubuserapp3.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var listUserData: ArrayList<UsersData> = ArrayList()
    private lateinit var usersDataAdapter: UsersDataAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersDataAdapter = UsersDataAdapter(listUserData)
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        searchData()
        recyclerViewUser()
        showGetDataGit()
        showUserViewModel(usersDataAdapter)

    }

    private fun showProgressbar(state: Boolean) {
        if (state) {
            pbMainActivity.visibility = View.VISIBLE
        } else {
            pbMainActivity.visibility = View.INVISIBLE
        }
    }

    private fun recyclerViewUser() {
        rc_MainActivity.layoutManager = LinearLayoutManager(requireActivity())
        rc_MainActivity.setHasFixedSize(true)
        usersDataAdapter.notifyDataSetChanged()
        rc_MainActivity.adapter = usersDataAdapter
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.title_alert)
        builder.setMessage(R.string.message_alert)
        builder.setPositiveButton("OK", { _,_ ->})
        builder.show()
    }

    private fun showUserViewModel(usersDataAdapter: UsersDataAdapter) {
        userViewModel.getAllUsers().observe(requireActivity(), { listUsers ->
            if (listUsers.size == 0) {
                showDialog()
                showProgressbar(false)
            } else if (listUsers != null) {
                usersDataAdapter.setData(listUsers)
                showProgressbar(false)
            }
        })

    }

    private fun showGetDataGit() {
        userViewModel.getGitDataUser(requireContext())
        showProgressbar(true)

    }

    private fun searchData() {
        search_user.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listUserData.clear()
                    recyclerViewUser()
                    userViewModel.getUserSearch(query, requireContext())
                    showProgressbar(true)
                    showUserViewModel(usersDataAdapter)
                    recyclerViewUser()
                } else {
                    return true
                }

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //fungsi untuk mengembalikan data
                listUserData.clear()
                recyclerViewUser()
                showGetDataGit()
                showUserViewModel(usersDataAdapter)
                return true
            }

        })
    }
}