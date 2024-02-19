package com.takeawaytest.common

import android.app.Activity
import android.content.Context
import com.google.gson.Gson

object Utility {


    fun saveUserInfo(info: Result?, activity: Activity) {
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
        return gson.fromJson(json, Result::class.java)
    }

    fun getBaseMessage(activity: Activity, title: String, mess: String, icon: Int, status: Int) {

        when (status) {
            0 -> {
                /*
                Alerter.create(activity)
                    .setTitle(title)
                    .setText(mess)
                    .setIcon(icon)
                    .setBackgroundColorRes(R.color.orange)
                    // .setIconColorFilter(0) // Optional - Removes white tint
                    // .setIconSize(38) // Optional - default is 38dp
                    .show()

                 */
            }
            1 -> {
                /*
                Alerter.create(activity)
                    .setTitle(title)
                    .setText(mess)
                    .setIcon(icon)
                    .setBackgroundColorRes(R.color.crown_icon_color)
                    // .setIconColorFilter(0) // Optional - Removes white tint
                    // .setIconSize() // Optional - default is 38dp
                    .show()

                 */
            }
            else -> {
                /*
                Alerter.create(activity)
                    .setTitle(title)
                    .setText(mess)
                    .setIcon(icon)
                    .setBackgroundColorRes(R.color.reject)
                    //.setIconColorFilter(0) // Optional - Removes white tint
                    // .setIconSize(38) // Optional - default is 38dp
                    .show()

                 */
            }
        }
    }

}