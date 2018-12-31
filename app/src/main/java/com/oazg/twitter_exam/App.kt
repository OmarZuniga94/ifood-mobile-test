package com.oazg.twitter_exam

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.oazg.twitter_exam.utils.Preferences
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig


class App : MultiDexApplication() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        private var prefs: Preferences? = null

        fun getContext(): Context {
            return instance!!.applicationContext
        }

        fun getPreferences(): Preferences {
            return prefs!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(getContext())
        prefs = Preferences(this)
        Stetho.initializeWithDefaults(getContext())
        val config = TwitterConfig.Builder(this).logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
                .debug(true).build()
        Twitter.initialize(config)
    }
}