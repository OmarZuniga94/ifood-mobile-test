package com.oazg.twitter_exam.modules.search

import android.view.View

class SearchContracts {
    interface Presenter {
        fun initTwitterButton()
        fun onUsersFound(users: Array<String?>)
        fun onUserNotFound()
        fun onTweetsFound(adapter: TweetsAdapter)
        fun onTweetsNotFound()
    }

    interface Iteractor {
        fun searchUser(input: String)
        fun displayTweets(userId: Long, listener: UsersItemClick)
    }

    interface Router {
        fun presentAnalysis()
    }

    interface UsersItemClick {
        fun onListItemClick(v: View, position: Int)
    }
}