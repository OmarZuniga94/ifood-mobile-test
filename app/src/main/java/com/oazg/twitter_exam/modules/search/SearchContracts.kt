package com.oazg.twitter_exam.modules.search

import android.view.View

class SearchContracts {
    interface Presenter {
        fun initTwitterButton()
        fun onUsersFound(adapter: Array<String?>)
        fun onUserNotFound()
    }

    interface Iteractor {
        fun searchUser(input: String, listener: UsersItemClick)
    }

    interface Router {
        fun presentAnalysis()
    }

    interface UsersItemClick {
        fun onListItemClick(v: View, position: Int)
    }
}