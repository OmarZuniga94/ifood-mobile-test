package com.oazg.twitter_exam.network

import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

open class CustomTwitterClient(session: TwitterSession) : TwitterApiClient(session) {

    fun getCustomService(): TwitterAPICustomService {
        return getService(TwitterAPICustomService::class.java)
    }

    interface TwitterAPICustomService {
        @GET("/1.1/users/search.json")
        fun searchUser(@Query("q") username: String): Call<ArrayList<User>>

        @GET("/1.1/statuses/user_timeline.json")
        fun searchTweets(@Query("user_id")userId:Long):Call<ArrayList<Tweet>>
    }
}