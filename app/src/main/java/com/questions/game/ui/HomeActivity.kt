package com.questions.game.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.Firebase
import com.google.firebase.initialize

import com.questions.game.app.gamedetails.GameDetails
import com.questions.game.databinding.ActivityHomeBinding
import com.questions.game.utils.Constant
import com.questions.game.utils.LogUtil.e
import com.questions.game.utils.Pref
import com.questions.game.utils.Utils
import java.util.Arrays

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Pref.initialize(this)

//        MobileAds.initialize(this) {}

//        val testDeviceIds = Arrays.asList("3E2B40644EE1C10688C352FFA6C97AC0")
//        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = object: AdListener() {
            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                e("Ad Error",adError)
            }
        }


        updateQbText()

        binding.btnPlaynewgame.setOnClickListener {
            startActivity(Intent(this, GameDetails::class.java))
        }
        binding.btnResumegame.setOnClickListener {
            startActivity(Intent(this, Test::class.java))
        }

    }

    private fun updateQbText() {
        binding.txtMainqb.text = Pref.getIntValue(Constant.userScorePref).toString()
    }

}