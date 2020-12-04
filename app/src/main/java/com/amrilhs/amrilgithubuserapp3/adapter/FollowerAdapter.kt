package com.amrilhs.amrilgithubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.model.UsersFollower
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class FollowerAdapter(private val listFollower: ArrayList<UsersFollower>) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(usersFollower: UsersFollower) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(usersFollower.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(list_avatar)
                list_username.text =
                    itemView.context.getString(R.string.det_username, usersFollower.username)
                list_fullname.text = usersFollower.name
                list_repository.text =
                    itemView.context.getString(R.string.repository, usersFollower.repository)
                list_follower.text =
                    itemView.context.getString(R.string.follower, usersFollower.followers)
                list_following.text =
                    itemView.context.getString(R.string.following, usersFollower.following)
            }
        }
    }

    fun setFollower(items: ArrayList<UsersFollower>) {
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_user,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }
}