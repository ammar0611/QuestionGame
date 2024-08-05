package com.example.questiongame.utils

import android.graphics.Color

object Constant{

    const val BASEURL="https://arshikweb.com/"
    const val PREF_NAME="MyPref"

    val color_correct= Color.parseColor("#FF4BBD04")
    val color_wrong= Color.parseColor("#FFF22727")
    var isChecked:Boolean=false
    var selectedItem:Int=0
    val selectedItems = mutableSetOf<Int>()

}