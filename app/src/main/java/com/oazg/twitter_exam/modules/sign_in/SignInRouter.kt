package com.oazg.twitter_exam.modules.sign_in

import android.app.Activity
import android.content.Intent
import com.oazg.twitter_exam.modules.search.SearchActivity

class SignInRouter(val activity: Activity) : SignInContracts.Reouter {

    override fun presentSearchScreen() {
        activity.startActivity(Intent(activity, SearchActivity::class.java))
        activity.finish()
    }
}