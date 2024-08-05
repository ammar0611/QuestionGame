package com.example.questiongame.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.questiongame.utils.Constant.PREF_NAME

object Pref {

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(context: Context?) {
        sharedPreferences = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setValue(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key,value)?.apply()
    }
    fun getValue(key: String):String{
        return sharedPreferences?.getString(key,"") ?:""
    }
}