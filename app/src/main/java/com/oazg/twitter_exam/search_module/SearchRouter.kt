package com.oazg.twitter_exam.search_module

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.oazg.twitter_exam.sentiment_fragment.SentimentFragment
import com.oazg.twitter_exam.utils.AnalyzeTweet

class SearchRouter(val activity: Activity) : SearchContracts.Router {

    override fun displayAnalyzeDialog(tweet: AnalyzeTweet, fm: FragmentManager) {
        val dialogFragment = SentimentFragment.newInstance(tweet)
        dialogFragment.show(fm, SentimentFragment.javaClass.simpleName)
    }
}