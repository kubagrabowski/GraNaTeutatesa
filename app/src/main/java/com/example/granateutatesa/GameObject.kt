package com.example.granateutatesa

import android.graphics.Canvas

interface GameObject {

    fun draw(canvas:Canvas)
    fun update()
}