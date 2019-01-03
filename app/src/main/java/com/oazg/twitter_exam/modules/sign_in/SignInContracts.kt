package com.oazg.twitter_exam.modules.sign_in

class SignInContracts {
    interface Presenter {
        fun initTwitterButton()
    }

    interface Reouter {
        fun presentSearchScreen()
    }
}