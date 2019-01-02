package com.oazg.twitter_exam.search_module

import android.view.View

class SearchContracts {
    interface Presenter {
        fun initTwitterButton()
        fun onUsersFound(users: Array<String?>)
        fun onTweetsFound(adapter: TweetsAdapter)
        fun onSearchFailed(msg: String)
        fun onAnalyzeFailed(msg: String)
    }

    interface Iteractor {
        fun searchUser(input: String)
        fun displayTweets(userId: Long, listener: UsersItemClick)
        fun analyzeTweet(tweet: String)
    }

    interface UsersItemClick {
        fun onListItemClick(v: View, position: Int)
    }
}