package com.amrilhs.amrilgithubuserapp3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.model.UsersFollowing
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class FollowingAdapter(private val listFollowing: ArrayList<UsersFollowing>) :
    RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(usersFollowing: UsersFollowing) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(usersFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(list_avatar)
                list_username.text =
                    itemView.context.getString(R.string.det_username, usersFollowing.username)
                list_fullname.text = usersFollowing.name
                list_repository.text =
                    itemView.context.getString(R.string.repository, usersFollowing.repository)
                list_follower.text =
                    itemView.context.getString(R.string.follower, usersFollowing.followers)
                list_following.text =
                    itemView.context.getString(R.string.following, usersFollowing.following)

            }
        }
    }

    fun setFollowing(items: ArrayList<UsersFollowing>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        return FollowingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_user,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }
}