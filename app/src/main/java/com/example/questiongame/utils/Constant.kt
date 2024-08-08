package com.example.questiongame.utils

import android.graphics.Color
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

object Constant{

    const val BASEURL="https://arshikweb.com/"
    const val PREF_NAME="MyPref"

    val color_correct= Color.parseColor("#FF4BBD04")
    val color_wrong= Color.parseColor("#FFF22727")
    var isChecked:Boolean=false
    var selectedItem:Int=0
    val selectedItems = mutableSetOf<Int>()

    val party = Party(speed = 2f,maxSpeed = 30f,damping = 0.9f,spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        position = Position.Relative(0.5, 0.3),
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100))

    fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 50L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }

}