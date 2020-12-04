package com.amrilhs.amrilgithubuserapp3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrilhs.amrilgithubuserapp3.DetailActivity
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class UsersDataAdapter(private val listUsersgit: ArrayList<UsersData>) :
    RecyclerView.Adapter<UsersDataAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userData: UsersData) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(userData.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(list_avatar)
                list_username.text =
                    itemView.context.getString(R.string.det_username, userData.username)
                list_fullname.text = userData.name
                list_repository.text =
                    itemView.context.getString(R.string.repository, userData.repository)
                list_follower.text =
                    itemView.context.getString(R.string.follower, userData.followers)
                list_following.text =
                    itemView.context.getString(R.string.following, userData.following)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_user,
                parent, false
            )
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(listUsersgit[position])
        val usersData = listUsersgit[position]
        holder.itemView.setOnClickListener {
            val usersDataIntent = UsersData(
                usersData.avatar,
                usersData.username,
                usersData.name,
                usersData.company,
                usersData.location,
                usersData.repository,
                usersData.followers,
                usersData.following
            )

            val moveIntent = Intent(it.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_DETAIL, usersDataIntent)
            it.context.startActivity(moveIntent)
        }
    }

    override fun getItemCount(): Int {
        return listUsersgit.size
    }

    fun setData(items: ArrayList<UsersData>) {
        listUsersgit.clear()
        listUsersgit.addAll(items)
        notifyDataSetChanged()
    }
}