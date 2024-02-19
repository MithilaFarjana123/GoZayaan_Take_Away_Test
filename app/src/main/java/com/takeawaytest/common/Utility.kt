package com.takeawaytest.common

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.takeawaytest.R
import com.takeawaytest.data.Model.Result

object Utility {


    fun saveUserInfo(info: Result, activity: Activity) {
        val mPrefs =
            activity.getSharedPreferences(activity.packageName, Context.MODE_PRIVATE).edit()
        val gson = Gson()
        val json = gson.toJson(info)
        mPrefs.putString("UserInfo", json)
        mPrefs.commit()
    }

    fun getUserInfo(activity: Activity): Result? {
        val prefs = activity.getSharedPreferences(activity.packageName, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("UserInfo", "")
        return gson.fromJson(json,Result::class.java)
    }


}