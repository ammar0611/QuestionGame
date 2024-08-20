package com.questions.game.utils

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
    }
}
