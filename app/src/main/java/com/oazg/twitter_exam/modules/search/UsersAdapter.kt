package com.oazg.twitter_exam.modules.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.databinding.RowUsersBinding
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.models.User

class UsersAdapter(val items: MutableList<User>, val context: Context, val listener: SearchContracts.UsersItemClick) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RowUsersBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.cslRowUsers.setOnClickListener { v -> listener.onListItemClick(v!!, position) }
    }
}

class ViewHolder(val binding: RowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
        with(binding) {
            txtUserName.text = "@".plus(item.screenName)
            txtUserFollowers.text = App.getContext().getString(R.string.row_followers).plus(item.followersCount)
            txtUserFriends.text = App.getContext().getString(R.string.row_friends).plus(item.friendsCount)
            Picasso.with(App.getContext()).load(item.profileImageUrl).resize(100, 100)
                .into(imgUserPhoto)
        }
    }
}