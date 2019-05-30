package com.example.granateutatesa

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class GemDobry(startx:Int ,starty:Int ,width:Int, height:Int, color:Int) : GameObject,Gem {

    private val GemValue = 1
    private val mojareprezentacja: Rect = Rect(startx, starty, startx+width, starty+height)

    override fun getGemZarys(): Rect {
        return mojareprezentacja
    }

    override fun getGemValue(): Int {
        return GemValue
    }

    override fun spadekpoY(y:Float) {
        mojareprezentacja.top+=y.toInt()
        mojareprezentacja.bottom+=y.toInt()

    }

    override fun draw(canvas: Canvas) {
        val paint: Paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(mojareprezentacja, paint)
    }

    override fun update() {

    }

    override fun colected(player:Player):Boolean{
        if(player.getPlayerRect().contains(mojareprezentacja.right-mojareprezentacja.width()/2, mojareprezentacja.bottom-mojareprezentacja.height()/4)){
            return true
        }

        return false
    }
}