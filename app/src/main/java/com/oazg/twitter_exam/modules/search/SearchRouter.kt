package com.oazg.twitter_exam.modules.search

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.util.Pair
import android.view.View
import com.oazg.twitter_exam.modules.analyze.AnalysisActivity
import com.oazg.twitter_exam.modules.analyze.BUNDLE_TWEET
import com.oazg.twitter_exam.utils.AnalyzeTweet

class SearchRouter(val activity: Activity) : SearchContracts.Router {

    override fun displayAnalyzeDialog(tweet: AnalyzeTweet, viewTransition: View) {
        val intent = Intent(activity, AnalysisActivity::class.java)
        intent.putExtra(BUNDLE_TWEET, tweet)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, Pair.create(viewTransition, "rowTransition"))
            activity.startActivity(intent, options.toBundle())
        } else {
            activity.startActivity(intent)
        }
    }
}