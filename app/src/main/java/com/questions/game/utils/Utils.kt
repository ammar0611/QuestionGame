package com.questions.game.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.google.gson.Gson
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object Utils {

    var isSkippable = true
    var is15sec = true

    fun mapToString(map: MutableMap<Int, MutableList<Int>>): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    fun stringToMap(string: String): MutableMap<Int, MutableList<Int>> {
        val gson = Gson()
        val mapType = object : com.google.gson.reflect.TypeToken<Map<Int, MutableList<Int>>>() {}.type
        return gson.fromJson(string, mapType)
    }

    fun getCurrentTimestampInIST(): String {
        val instant = Instant.now()
        val zonedDateTimeInIST = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Kolkata"))
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")
        return zonedDateTimeInIST.format(formatter)
    }

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

    val adjective = arrayOf("Sleepy",
        "Calm",
        "Cute",
        "Funny",
        "Naughty",
        "Jolly",
        "Curious",
        "Happy",
        "Energetic",
        "Jumpy",
        "Dancing",
        "Busy",
        "Dashing",
        "Shy",
        "Sparky",
        "Hungry",
        "Quick",
        "Creative",
        "Strong",
        "Tiny",
        "Lovely",
        "Joyful",
        "Inspired",
        "Little",
        "Large",
        "Spiky",
        "Stingy",
        "Slow",
        "Smiling",
        "Pink",
        "Green",
        "Orange",
        "Blue",
        "Purple",
        "Dreamy",
        "Bouncy",
        "Playful",
        "Cool",
        "Amazing",
        "Bright",
        "Fast",
        "Blazing",
        "Silver",
        "Spontaneous",
        "Agressive",
        "Inspired",
        "Golden",
        "Secretive",
        "Brave",
        "Victorious")

    val animals = arrayOf("Bee",
        "Butterfly",
        "Tiger",
        "Giraffe",
        "Panda",
        "Impala",
        "Turtle",
        "Bunny",
        "Bear",
        "Kitten",
        "Puppy",
        "Lion",
        "Snake",
        "Hippo",
        "Dino",
        "Rhino",
        "Snail",
        "Alpaca",
        "Hamster",
        "Spider",
        "Caterpillar",
        "Cub",
        "Wolf",
        "Alligator",
        "Cobra",
        "Chick",
        "Robin",
        "Tortoise",
        "Walrus",
        "Krakken",
        "Squid",
        "Mouse",
        "Cricket",
        "Boar",
        "Mantis",
        "Dolphin",
        "Shark",
        "Bird",
        "Fish",
        "Eagle",
        "Whale",
        "Minnow",
        "Sparrow",
        "Hawk",
        "Fox",
        "Rabbit",
        "Octopus",
        "Faun",
        "Quail",
        "Calf")

}