package com.oazg.twitter_exam.modules.analyze

class AnalysisContracts {
    interface Presenter {
        fun initToolbar()
        fun initViews()
    }

    interface Router {
        fun presentTweetsScreen()
    }
}