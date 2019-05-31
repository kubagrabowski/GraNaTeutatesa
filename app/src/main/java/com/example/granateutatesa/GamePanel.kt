package com.example.granateutatesa

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception

class GamePanel(cont: Context, val activity:GameActivity, points:Int): SurfaceView(cont), SurfaceHolder.Callback {

    companion object {
        var SCREEN_WIDTH = 0
        var SCREEN_HEIGHT = 0
        var INIT_MOMENT = 0L
    }


    var mythread : MainThread? = null
    var player : Player? = null
    var player_point : Point? = null
    var gemMenager = GemMenager(100,75,75, cont)
    var lives = 10
    var points = 0
    val pointsTarget = points
    //var movingplayer = false

    init{
        holder.addCallback(this)
        mythread = MainThread(holder, this)
        setFocusable(true)


        player = Player(Rect(SCREEN_WIDTH/2-100, SCREEN_HEIGHT-225,SCREEN_WIDTH/2+100, SCREEN_HEIGHT-100), Color.rgb(255,255,0), cont)
        player_point = Point(SCREEN_WIDTH/2,SCREEN_HEIGHT-175)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        INIT_MOMENT = System.currentTimeMillis()
        mythread = MainThread(getHolder(), this)

        mythread!!.setRunnin(true)
        mythread!!.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        var retry = true
        while(retry){
            try{
                mythread!!.setRunnin(false)
                mythread!!.join()
            }catch(e:Exception){ Log.d("Panel", e.toString())}
            retry = false
        }
    }

    /*override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event!!.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                if(lives > 0 && player!!.getPlayerRect().contains(event.x.toInt(),event.y.toInt())){
                    movingplayer = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if(lives>0 && movingplayer)
                player_point!!.set(event.x.toInt(), player_point!!.y)
            }
            MotionEvent.ACTION_UP -> {
                movingplayer = false
            }
        }

        return true

    }*/

    fun update(){
        if(lives>0) {
            //player!!.update(player_point!!)
            player!!.update()
            gemMenager.update()
            val zwrot = gemMenager.checkCollected(player!!)
            if(zwrot != 0){
                cosZebrane(zwrot)
                if(lives==0){
                    player!!.stopSensor()
                    activity.end(false)
                }
                if(points>= pointsTarget){
                    player!!.stopSensor()
                    activity.end(true)
                }
            }
        }
    }

    fun cosZebrane(value:Int){
        when(value){
            1 -> points++
            2 -> lives--
            3 -> gemMenager.speedUp()
            4 -> gemMenager.speedDown()
            5 -> {lives++; points = points+3}
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawColor(Color.WHITE)
        player!!.draw(canvas)
        gemMenager.draw(canvas)
        drawText(canvas,lives.toString(), 200f, 0f)
        drawText(canvas,"Punkty "+points.toString(), SCREEN_WIDTH.toFloat(), 0f)
    }

    fun drawText(canvas:Canvas, tekst:String, x:Float, y:Float){
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100.0f
        paint.textAlign = Paint.Align.LEFT
        val r = Rect()
        canvas.getClipBounds(r)

        paint.getTextBounds(tekst,0,tekst.length,r)

        canvas.drawText(tekst,x-r.width()-50f,y + r.height() + 50f,paint)

    }
}