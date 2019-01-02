package com.oazg.twitter_exam.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Preferences(val context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveDataBool(key: Int, data: Boolean) {
        val editor = this.preferences.edit()
        editor.putBoolean(context.getString(key), data)
        editor.commit()
    }

    fun loadDataBoolean(key: Int, defValue: Boolean): Boolean {
        return this.preferences.getBoolean(context.getString(key), defValue)
    }
}