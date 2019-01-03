package com.oazg.twitter_exam.modules.analyze

import android.app.Activity

class AnalysisRouter(val activity:Activity):AnalysisContracts.Router {

    override fun presentTweetsScreen() {
        activity.finish()
    }
}