package com.oazg.twitter_exam.modules.search

import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.utils.CustomTwitterClient
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User


class SearchIteractor(val presenter: SearchContracts.Presenter) : SearchContracts.Iteractor {

    lateinit var usersList: MutableList<User>
    lateinit var tweetsList: MutableList<Tweet>

    override fun searchUser(input: String) {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        object : CustomTwitterClient(session) {}.getCustomService().searchUser(input)
            .enqueue(object : Callback<ArrayList<User>>() {
                override fun success(result: Result<ArrayList<User>>?) {
                    usersList = result!!.data.toMutableList()
                    if (usersList.size > 0) {
                        presenter.onUsersFound(getUsersArray())
                    } else {
                        presenter.onUserNotFound()
                    }
                }

                override fun failure(exception: TwitterException) {
                    presenter.onUserNotFound()
                }
            })
    }

    override fun displayTweets(userId: Long, listener: SearchContracts.UsersItemClick) {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        object : CustomTwitterClient(session) {}.getCustomService().searchTweets(userId)
            .enqueue(object : Callback<ArrayList<Tweet>>() {
                override fun success(result: Result<ArrayList<Tweet>>?) {
                    tweetsList = result!!.data.toMutableList()
                    if (tweetsList.size > 0) {
                        presenter.onTweetsFound(TweetsAdapter(tweetsList, App.getContext(), listener))
                    } else {
                        presenter.onTweetsNotFound()
                    }
                }

                override fun failure(exception: TwitterException?) {
                    presenter.onTweetsNotFound()
                }
            })
    }

    private fun getUsersArray(): Array<String?> {
        val users = arrayOfNulls<String>(usersList.size)
        var i = 0
        while (i <= users.size - 1) {
            users[i] = usersList[i].screenName
            i++
        }
        return users
    }
}