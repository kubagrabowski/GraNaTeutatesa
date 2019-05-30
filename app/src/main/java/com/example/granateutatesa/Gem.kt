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

/*
class Gem(startx:Int ,starty:Int ,width:Int, height:Int, var color:Int) : GameObject {

    private val mojareprezentacja:Rect = Rect(startx, starty, startx+width, starty+height)

    fun getGemZarys():Rect{
        return mojareprezentacja
    }

    fun spadekpoY(y:Float) {
        mojareprezentacja.top+=y.toInt()
        mojareprezentacja.bottom+=y.toInt()

    }

    override fun draw(canvas: Canvas) {
        val paint:Paint = Paint()
        paint.color = color
        canvas.drawRect(mojareprezentacja, paint)
    }

    override fun update() {

    }

    fun colected(player:Player):Boolean{
        if(player.getPlayerRect().contains(mojareprezentacja.right-mojareprezentacja.width()/2, mojareprezentacja.bottom-mojareprezentacja.height()/4)){
            return true
        }

        return false
    }
}*/