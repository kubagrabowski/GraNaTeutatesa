package com.example.granateutatesa

import android.graphics.Canvas
import android.graphics.Rect

interface Gem:GameObject{
    fun getGemZarys():Rect
    fun getGemValue():Int
    fun spadekpoY(y:Float)
    override fun draw(canvas:Canvas)
    override fun update()
    fun colected(player:Player):Boolean
}

