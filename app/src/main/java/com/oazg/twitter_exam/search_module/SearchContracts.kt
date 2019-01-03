package com.oazg.twitter_exam.search_module

import android.view.View
import androidx.fragment.app.FragmentManager
import com.oazg.twitter_exam.adapter.TweetsAdapter
import com.oazg.twitter_exam.utils.AnalyzeTweet
import com.twitter.sdk.android.core.models.Tweet

class SearchContracts {
    interface Presenter {
        fun initTwitterButton()
        fun onUsersFound(users: Array<String?>)
        fun onTweetsFound(adapter: TweetsAdapter)
        fun onTweetAnalyze(tweet: AnalyzeTweet)
        fun onSearchFailed(msg: String)
        fun onAnalyzeFailed(msg: String)
    }

    interface Iteractor {
        fun searchUser(input: String)
        fun displayTweets(userId: Long, listener: UsersItemClick)
        fun analyzeTweet(tweet: Tweet)
    }

    interface Router {
        fun displayAnalyzeDialog(tweet: AnalyzeTweet, fm: FragmentManager)
    }

    interface UsersItemClick {
        fun onListItemClick(v: View, position: Int)
    }
}