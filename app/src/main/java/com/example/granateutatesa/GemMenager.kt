package com.example.granateutatesa

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log

class GemMenager(val gapBetween:Int, val gemheight:Int, val gemwidth:Int, val color: Int) {

    var gems:ArrayList<Gem> = ArrayList()

    var startTimeMilis:Long = System.currentTimeMillis()
    var travelScreenTime = 5000.0f

    var isSpeedUp = false
    var isSpeedDown = false
    val timeSpeedChange = 5000.0f
    val SpeedChange = 1500.0f
    var timeChangedUp = 0L
    var timeChangedDown = 0L

    init{
        tworzGemy()
    }

    fun tworzGemy(){
        var y_niebotworzenia = -2*GamePanel.SCREEN_HEIGHT
        while(y_niebotworzenia < 0){
            var xGem = (Math.random()*GamePanel.SCREEN_WIDTH).toInt()
            if (xGem+gemwidth>GamePanel.SCREEN_WIDTH){
                xGem = GamePanel.SCREEN_WIDTH-gemwidth
            }
            //gems.add(GemDobry(xGem,y_niebotworzenia,gemwidth,gemheight,color))
            gems.add(losujGem(xGem,y_niebotworzenia,gemwidth,gemheight,color))
            y_niebotworzenia+=gapBetween+gemheight
        }
    }

    fun losujGem(startx:Int ,starty:Int ,width:Int, height:Int, color:Int):Gem{
        val los = (Math.random() * 100).toInt()
        Log.d("LOS", los.toString())
        when(los){
            in 0..10 ->{
                return GemSpeedUp(startx,starty,width,height,color)
            }
            in 11..49 ->{
                return GemDobry(startx,starty,width,height,color)
            }
            50 -> {
                return GemZycie(startx,starty,width,height,color)
            }
            in 51..90 ->{
                return GemZly(startx,starty,width,height,color)
            }
            else -> {
                return GemSpeedDown(startx,starty,width,height,color)
            }

        }

    }

    fun checkCollected(player:Player):Int{
        var zwrot = 0
        for(gem:Gem in gems){
            if(gem.colected(player)){
                zwrot = gem.getGemValue()
                gems.remove(gem)
                utrzymajRownowage()
                return zwrot
            }
        }
        return 0
    }

    fun utrzymajRownowage(){
        var xGem = (Math.random()*GamePanel.SCREEN_WIDTH).toInt()
        if (xGem+gemwidth>GamePanel.SCREEN_WIDTH){
            xGem = GamePanel.SCREEN_WIDTH-gemwidth
        }
        //gems.add(0,GemDobry(xGem,gems[0].getGemZarys().top-gapBetween-gemheight,gemwidth,gemheight,color))
        gems.add(0,losujGem(xGem,gems[0].getGemZarys().top-gapBetween-gemheight,gemwidth,gemheight,color))

    }

    fun speedUp(){
        if(!isSpeedUp) {
            timeChangedUp = System.currentTimeMillis()
            travelScreenTime -= SpeedChange
            isSpeedUp = true
        }
    }

    fun speedDown(){
        if(!isSpeedDown) {
            timeChangedDown = System.currentTimeMillis()
            travelScreenTime += SpeedChange
            isSpeedDown = true
        }
    }

    fun update(){
        if(startTimeMilis<GamePanel.INIT_MOMENT){
            startTimeMilis = GamePanel.INIT_MOMENT
        }
        if(isSpeedUp){
            if(System.currentTimeMillis()-timeChangedUp>timeSpeedChange){
                travelScreenTime += SpeedChange
                isSpeedUp = false
            }
        }
        if(isSpeedDown){
            if(System.currentTimeMillis()-timeChangedDown>timeSpeedChange){
                travelScreenTime -= SpeedChange
                isSpeedDown = false
            }
        }


        val elapsedTime = (System.currentTimeMillis() - startTimeMilis).toInt()
        startTimeMilis = System.currentTimeMillis()
        val speed:Float = GamePanel.SCREEN_HEIGHT/travelScreenTime
        for(gem:Gem in gems){
            gem.spadekpoY(speed*elapsedTime)
        }
        if(gems[gems.size-1].getGemZarys().top>=GamePanel.SCREEN_HEIGHT){
            /*var xGem = (Math.random()*GamePanel.SCREEN_WIDTH).toInt()
            if (xGem+gemwidth>GamePanel.SCREEN_WIDTH){
                xGem = GamePanel.SCREEN_WIDTH-gemwidth
            }*/
            gems.removeAt(gems.size-1)
            utrzymajRownowage()
        }

    }

    fun draw(canvas: Canvas){
        for(gem:Gem in gems){
            gem.draw(canvas)
        }
    }

}