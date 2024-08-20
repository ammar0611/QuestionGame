package com.questions.game.utils

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import com.questions.game.R
import com.questions.game.utils.LogUtil.e
import java.util.Objects

private var dialog: Dialog? = null

// Function to show progress bar
fun showProgress(activity: Activity,isCancellable:Boolean) {
    try {
        if (dialog == null || !dialog!!.isShowing) {
            dialog = Dialog(activity, android.R.style.Theme_Light)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            Objects.requireNonNull<Window?>(dialog!!.window)
                .setBackgroundDrawableResource(R.color.black_bg)
            dialog!!.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog!!.setContentView(R.layout.progress)
            if (!isCancellable) {dialog!!.setCancelable(false)}
            if (!activity.isFinishing) {
                dialog!!.show()
            }
        } else if (!dialog!!.isShowing) {
            if (!activity.isFinishing) {
                dialog!!.show()
            }
        }
    } catch (e: Exception) {
        e("showProgress : ", e.message!!)
    }
}

fun hideProgress() {
    try {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
    } catch (ignored: Exception) {
    }
}