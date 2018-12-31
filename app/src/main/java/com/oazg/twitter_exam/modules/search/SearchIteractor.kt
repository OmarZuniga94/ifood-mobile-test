package com.oazg.twitter_exam.modules.search

import com.oazg.twitter_exam.App
import com.oazg.twitter_exam.R
import com.oazg.twitter_exam.utils.CustomTwitterClient
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.User


class SearchIteractor(val presenter: SearchContracts.Presenter) : SearchContracts.Iteractor {

    lateinit var usersList: MutableList<User>

    override fun searchUser(input: String, listener: SearchContracts.UsersItemClick) {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        object : CustomTwitterClient(session) {}.getCustomService().searchUser(input)
            .enqueue(object : Callback<ArrayList<User>>() {
                override fun success(result: Result<ArrayList<User>>?) {
                    usersList = result!!.data.toMutableList()
                    if (usersList.size > 0)
                        presenter.onUsersFound(UsersAdapter(usersList, App.getContext(), listener))
                    else
                        presenter.onUserNotFound()
                }

                override fun failure(exception: TwitterException) {
                    presenter.onUserNotFound()
                }
            })
    }
}