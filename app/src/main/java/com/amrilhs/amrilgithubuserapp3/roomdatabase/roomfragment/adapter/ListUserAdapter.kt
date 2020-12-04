package com.amrilhs.amrilgithubuserapp3.roomdatabase.roomfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub
import kotlinx.android.synthetic.main.list_room.view.*

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.userViewHolder>() {

    private var usergithublist = emptyList<UserGithub>()

    class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        return userViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_room,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currentItem = usergithublist[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.name_txt.text = currentItem.name.toString()
        holder.itemView.username_txt.text = currentItem.username.toString()
        holder.itemView.company_txt.text = currentItem.company.toString()
        holder.itemView.location_txt.text = currentItem.location.toString()
    }

    override fun getItemCount(): Int {
        return usergithublist.size
    }

    fun SetDataRooom(userGithub: List<UserGithub>) {
        this.usergithublist = userGithub
        notifyDataSetChanged()

    }
}