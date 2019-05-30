package com.example.granateutatesa

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(val surfHolder:SurfaceHolder, val gamePan:GamePanel):Thread() {

    val FPS = 30
    var avg_FPS = 0
    var isRunning:Boolean = false
    var canvas:Canvas? = null

    fun setRunnin(running: Boolean){
        isRunning = running
    }

    override fun run() {
        var startTime:Long = 0
        var timeinMiliS:Long = 0
        var waitTime:Long = 0
        var frameCount = 0
        var totalTime:Long = 0
        val targetTime:Long = (1000/FPS).toLong()


        while(isRunning){
            startTime = System.nanoTime()

            try {
               canvas = this.surfHolder.lockCanvas()
                synchronized(surfHolder){
                    this.gamePan.update()
                    this.gamePan.draw(canvas)
                }
            }catch(e:Exception){
                Log.d("Thread", e.toString())
            } finally {
                if(canvas != null){
                    try{
                       surfHolder.unlockCanvasAndPost(canvas)
                    }catch(e:Exception){Log.d("Thread", e.toString())}
                }
            }

            timeinMiliS = (System.nanoTime() - startTime)/1000000
            waitTime = targetTime - timeinMiliS
            try{
                if(waitTime>0){
                    sleep(waitTime)
                }
            }catch (e:Exception){Log.d("Thread", e.toString())}

            totalTime += System.nanoTime() - startTime
            frameCount++

            if(frameCount == FPS){
                avg_FPS = (1000/((totalTime/frameCount)/1000000)).toInt()
                frameCount = 0
                totalTime = 0
                Log.d("Thread", avg_FPS.toString())
            }

        }


    }

}