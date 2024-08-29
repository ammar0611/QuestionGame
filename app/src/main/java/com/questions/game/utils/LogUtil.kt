package com.questions.game.utils

import android.util.Log

object LogUtil {

    private const val SWITCH = true


    @JvmStatic
    fun v(TAG: String?, msg: Any) {
        if (SWITCH) Log.v(TAG, msg.toString())
    }
    @JvmStatic
    fun d(TAG: String?, msg: Any) {
        if (SWITCH) Log.d(TAG, msg.toString())
    }
    @JvmStatic
    fun i(TAG: String?, msg: Any) {
        if (SWITCH) Log.i(TAG, msg.toString())
    }
    @JvmStatic
    fun w(TAG: String?, msg: Any) {
        if (SWITCH) Log.w(TAG,msg.toString())
    }
    @JvmStatic
    fun e(TAG: String?, msg: Any) {
        if (SWITCH) Log.e(TAG,msg.toString())
    }
}