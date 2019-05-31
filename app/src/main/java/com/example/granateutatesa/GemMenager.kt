package com.example.granateutatesa

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log

class GemMenager(private val gapBetween:Int, private val gemheight:Int, private val gemwidth:Int, private val context:Context) {

    private var gems:ArrayList<Gem> = ArrayList()

    private var startTimeMilis:Long = System.currentTimeMillis()
    private var travelScreenTime = 5000.0f

    private var isSpeedUp = false
    private var isSpeedDown = false
    private val timeSpeedChange = 5000.0f
    private val speedChange = 1500.0f
    private var timeChangedUp = 0L
    private var timeChangedDown = 0L

    init{
        tworzGemy()
    }

    private fun tworzGemy(){
        var y_niebotworzenia = -2*GamePanel.SCREEN_HEIGHT
        while(y_niebotworzenia < 0){
            var xGem = (Math.random()*GamePanel.SCREEN_WIDTH).toInt()
            if (xGem+gemwidth>GamePanel.SCREEN_WIDTH){
                xGem = GamePanel.SCREEN_WIDTH-gemwidth
            }
            gems.add(losujGem(xGem,y_niebotworzenia,gemwidth,gemheight))
            y_niebotworzenia+=gapBetween+gemheight
        }
    }

    fun losujGem(startx:Int ,starty:Int ,width:Int, height:Int):Gem{
        val los = (Math.random() * 100).toInt()
        Log.d("LOS", los.toString())
        when(los){
            in 0..5 ->{
                val emerald = BitmapFactory.decodeResource(context.resources, R.drawable.emerald)
                return GemSpeedUp(startx,starty,width,height, emerald)
            }
            in 6..49 ->{
                val sapphire = BitmapFactory.decodeResource(context.resources, R.drawable.sapphire)
                return GemDobry(startx,starty,width,height, sapphire)
            }
            50 -> {
                val diamond = BitmapFactory.decodeResource(context.resources, R.drawable.diamond)
                return GemZycie(startx,starty,width,height, diamond)
            }
            in 51..95 ->{
                val ruby = BitmapFactory.decodeResource(context.resources, R.drawable.ruby)
                return GemZly(startx,starty,width,height, ruby)
            }
            else -> {
                val emerald = BitmapFactory.decodeResource(context.resources, R.drawable.emerald)
                return GemSpeedDown(startx,starty,width,height, emerald)
            }

        }

    }

    fun checkCollected(player:Player):Int{
        for(gem:Gem in gems){
            if(gem.colected(player)){
                val zwrot = gem.getGemValue()
                gems.remove(gem)
                utrzymajRownowage()
                return zwrot
            }
        }
        return 0
    }

    private fun utrzymajRownowage(){
        var xGem = (Math.random()*GamePanel.SCREEN_WIDTH).toInt()
        if (xGem+gemwidth>GamePanel.SCREEN_WIDTH){
            xGem = GamePanel.SCREEN_WIDTH-gemwidth
        }
        gems.add(0,losujGem(xGem,gems[0].getGemZarys().top-gapBetween-gemheight,gemwidth,gemheight))
    }

    fun speedUp(){
        if(!isSpeedUp) {
            timeChangedUp = System.currentTimeMillis()
            travelScreenTime -= speedChange
            isSpeedUp = true
        }
    }

    fun speedDown(){
        if(!isSpeedDown) {
            timeChangedDown = System.currentTimeMillis()
            travelScreenTime += speedChange
            isSpeedDown = true
        }
    }

    fun update(){
        if(startTimeMilis<GamePanel.INIT_MOMENT){
            startTimeMilis = GamePanel.INIT_MOMENT
        }

        if(isSpeedUp){
            if(System.currentTimeMillis()-timeChangedUp>timeSpeedChange){
                travelScreenTime += speedChange
                isSpeedUp = false
            }
        }
        if(isSpeedDown){
            if(System.currentTimeMillis()-timeChangedDown>timeSpeedChange){
                travelScreenTime -= speedChange
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