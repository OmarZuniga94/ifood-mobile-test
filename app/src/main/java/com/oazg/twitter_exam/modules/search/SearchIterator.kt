package com.oazg.twitter_exam.modules.search

import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.net.CustomTwitterClient
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.*

const val USER_NOT_FOUND: Long = -1
const val TWEETS_NOT_FOUND: Long = -2

class SearchIterator(val presenter: SearchContracts.Presenter) : SearchContracts.Iteractor {

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
                            /* If the search doesn't show any user then set custom text in Recycler View */
                            val item = Tweet(null, null, null, null,
                                    null, null, false, null, USER_NOT_FOUND,
                                    null, null, USER_NOT_FOUND, null,
                                    USER_NOT_FOUND, null, null, null, false,
                                    null, USER_NOT_FOUND, null, null, 0,
                                    false, null, null, App.getContext().getString(R.string.error_user_not_found),
                                    null, false, null, false,
                                    null, null, null)
                            val list = mutableListOf(item)
                            presenter.onTweetsFound(TweetsAdapter(list, App.getContext(), null))
                        }
                    }

                    override fun failure(exception: TwitterException) {
                        /* If the search doesn't response any user then display an error SnackBar */
                        presenter.onSearchFailed(exception.message!!)
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
                            /* If the search doesn't show any user then set custom text in Recycler View */
                            val item = Tweet(null, null, null, null,
                                    null, null, false, null, TWEETS_NOT_FOUND,
                                    null, null, TWEETS_NOT_FOUND, null,
                                    TWEETS_NOT_FOUND, null, null, null, false,
                                    null, TWEETS_NOT_FOUND, null, null, 0,
                                    false, null, null, App.getContext().getString(R.string.error_tweet_not_found),
                                    null, false, null, false,
                                    null, null, null)
                            val list = mutableListOf(item)
                            presenter.onTweetsFound(TweetsAdapter(list, App.getContext(), null))
                        }
                    }

                    override fun failure(exception: TwitterException) {
                        /* If the search doesn't response any user then display an error SnackBar */
                        presenter.onSearchFailed(exception.message!!)
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