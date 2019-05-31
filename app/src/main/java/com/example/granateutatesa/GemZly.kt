package com.example.granateutatesa

import android.graphics.*

class GemZly(startx:Int ,starty:Int ,width:Int, height:Int, private val bitmap:Bitmap) : GameObject,Gem {

    private val GemValue = 2
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
        canvas.drawBitmap(bitmap,null, mojareprezentacja,Paint())
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