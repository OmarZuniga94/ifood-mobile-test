package com.oazg.twitter_exam.search_module

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.databinding.RowUsersBinding
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.models.Tweet

class TweetsAdapter(
    val items: MutableList<Tweet>,
    val context: Context,
    val listener: SearchContracts.UsersItemClick?
) :
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
        if (listener != null)
            holder.binding.cslRowUsers.setOnClickListener { v -> listener.onListItemClick(v!!, position) }
    }
}

class ViewHolder(val binding: RowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Tweet) {
        with(binding) {
            if (item.id != USER_NOT_FOUND && item.id != TWEETS_NOT_FOUND) {
                txtErrorSearch.visibility = GONE
                cslRowUsers.visibility = VISIBLE
                txtUserName.text = item.user.name
                val date = item.createdAt.split(" ")
                txtUserScreenName.text = "@".plus(item.user.screenName)
                txtTweetDate.text = "Date: ".plus(date[2] + " " + date[1] + " " + date[5])
                txtUserTweet.text = item.text
                Picasso.with(App.getContext()).load(item.user.profileImageUrl).resize(40, 40)
                    .into(imgUserPhoto)
            } else {
                txtErrorSearch.visibility = VISIBLE
                cslRowUsers.visibility = GONE
                txtErrorSearch.text = item.text
            }
        }
    }
}