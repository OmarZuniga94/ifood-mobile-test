package com.oazg.twitter_exam.utils

import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

open class CustomTwitterClient(session: TwitterSession) : TwitterApiClient(session) {

    fun getCustomService(): GetUsersShowAPICustomService {
        return getService(GetUsersShowAPICustomService::class.java)
    }

    interface GetUsersShowAPICustomService {
        @GET("/1.1/users/search.json")
        fun searchUser(@Query("q") username: String): Call<ArrayList<User>>
    }
}