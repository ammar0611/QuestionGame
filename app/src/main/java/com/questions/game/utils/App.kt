package com.questions.game.utils

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.questions.game.utils.LogUtil.e
import dagger.hilt.android.HiltAndroidApp
import java.util.Arrays

@HiltAndroidApp
class App : Application() {

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        val testDeviceIds = Arrays.asList("4CFD846DBEB7765FA94CE9D6A87C92E8")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
        Firebase.initialize(this)
        MobileAds.initialize(this@App) {}
    }
}
