package com.example.granateutatesa

import android.graphics.*

class GemZycie(startx:Int ,starty:Int ,width:Int, height:Int, private val bitmap: Bitmap) : GameObject,Gem {

    private val GemValue = 5
    private val mojareprezentacja: Rect = Rect(startx, starty, startx+width, starty+height)
    private var lastChange = System.currentTimeMillis()
    private val blinkFreq = 500L

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
       /* val paint: Paint = Paint()
        if(System.currentTimeMillis()-lastChange>=blinkFreq) {
            if(System.currentTimeMillis()-lastChange>=2*blinkFreq)
                lastChange = System.currentTimeMillis()
            paint.color = Color.BLUE
        }
        else if(System.currentTimeMillis()-lastChange<=blinkFreq){
            paint.color = Color.CYAN
        }

        canvas.drawRect(mojareprezentacja, paint)*/

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