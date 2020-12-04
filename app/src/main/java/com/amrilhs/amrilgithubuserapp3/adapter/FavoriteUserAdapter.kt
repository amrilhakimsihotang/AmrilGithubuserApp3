package com.amrilhs.amrilgithubuserapp3.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrilhs.amrilgithubuserapp3.DetailActivity
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.model.UserFavorite
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class FavoriteUserAdapter(private val activity: Activity) :
    RecyclerView.Adapter<FavoriteUserAdapter.favoriteViewHolder>() {

    var listUserFavorite = ArrayList<UserFavorite>()
        set(listUserFavorite) {
            if (listUserFavorite.size > 0) {
                this.listUserFavorite.clear()
            }
            this.listUserFavorite.addAll(listUserFavorite)
            notifyDataSetChanged()
        }

    inner class favoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userFavorite: UserFavorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(userFavorite.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .error(R.drawable.ic_baseline_person_24)
                    .into(list_avatar)

                list_username.text =
                    itemView.context.getString(R.string.det_username, userFavorite.username)
                list_fullname.text = userFavorite.name
                list_repository.text =
                    itemView.context.getString(R.string.repository, userFavorite.repository)
                list_follower.text =
                    itemView.context.getString(R.string.follower, userFavorite.followers)
                list_following.text =
                    itemView.context.getString(R.string.following, userFavorite.following)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteViewHolder {
        return favoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_user,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: favoriteViewHolder, position: Int) {
        holder.bind(listUserFavorite[position])
        val dataUserFavorite = listUserFavorite[position]
        holder.itemView.setOnClickListener {
            val usersDataIntent = UsersData(
                dataUserFavorite.avatar,
                dataUserFavorite.username,
                dataUserFavorite.name,
                dataUserFavorite.company,
                dataUserFavorite.location,
                dataUserFavorite.repository,
                dataUserFavorite.followers,
                dataUserFavorite.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, usersDataIntent)
            it.context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return listUserFavorite.size
    }

}


