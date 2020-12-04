package com.amrilhs.amrilgithubuserapp3.roomdatabase.roomfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.roomdatabase.roomfragment.adapter.ListUserAdapter
import com.amrilhs.amrilgithubuserapp3.roomdatabase.roomviewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_list_usergithub.view.*


class ListUsergithubFragment : Fragment() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_list_usergithub,
            container, false
        )


        val listUserAdapter = ListUserAdapter()
        val recyclerView = view.recyclerviewRoom
        recyclerView.adapter = listUserAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.readAllData.observe(viewLifecycleOwner, Observer { usergithub ->
            listUserAdapter.SetDataRooom(usergithub)
        })
        return view
    }

}