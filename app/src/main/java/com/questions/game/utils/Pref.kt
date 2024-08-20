package com.questions.game.utils

import android.content.Context
import android.content.SharedPreferences
import com.questions.game.utils.Constant.PREF_NAME

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

    fun setIntValue(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key,value)?.apply()
    }
    fun getIntValue(key: String):Int{
        return sharedPreferences?.getInt(key,0) ?:0
    }

    fun setList(key: String, value:Set<String>){
        sharedPreferences?.edit()?.putStringSet(key,value)?.apply()
    }
    fun getList(key: String):Set<String>{
        return sharedPreferences?.getStringSet(key, emptySet()) ?: emptySet()
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key,value)?.apply()
    }
    fun getBoolean(key: String):Boolean{
        return sharedPreferences?.getBoolean(key,false) ?:false
    }

    fun clear(){
        sharedPreferences?.edit()?.clear()?.apply()
    }

}