package com.example.granateutatesa

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class Player(val jakwadrat:Rect, var color:Int, val context:Context):GameObject,SensorEventListener {

    val otoczka = Rect(jakwadrat)

    private val manager = (context.getSystemService(Context.SENSOR_SERVICE)) as(SensorManager)
    private val sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var rotation = 0.0f
    private val speedwhenRot = 10.0f

    init {
        register()
    }

    fun register(){
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stopSensor(){
        manager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            rotation = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            Log.d("SENSORX", rotation.toString())
            Log.d("SENSORY", y.toString())
            Log.d("SENSORZ", z.toString())

        }
    }



    override fun draw(canvas: Canvas) {
        var paint:Paint = Paint()
        paint.color = Color.BLACK
        canvas.drawRect(otoczka, paint)
        paint.color = color
        canvas.drawRect(jakwadrat, paint)

    }

    override fun update() {
        if(-1.0f < rotation && rotation < 1.0f ){
            rotation=0.0f
        }
        Log.d("ROT", (speedwhenRot/rotation).toString())
        jakwadrat.set(jakwadrat.left-(speedwhenRot*rotation).toInt(),jakwadrat.top, jakwadrat.right-(speedwhenRot*rotation).toInt(), jakwadrat.bottom)
        if(jakwadrat.left<10){
            jakwadrat.set(10,jakwadrat.top, jakwadrat.width()+10, jakwadrat.bottom)
        }
        if(jakwadrat.right>GamePanel.SCREEN_WIDTH-10){
            jakwadrat.set(GamePanel.SCREEN_WIDTH-jakwadrat.width()-10,jakwadrat.top, GamePanel.SCREEN_WIDTH-10, jakwadrat.bottom)
        }
        otoczka.set(jakwadrat)
        otoczka.set(otoczka.left-10,otoczka.top,otoczka.right+10,otoczka.bottom+10)
    }

    fun update(point:Point){
        jakwadrat.set(point.x-jakwadrat.width()/2, point.y - jakwadrat.height()/2, point.x + jakwadrat.width()/2, point.y + jakwadrat.height()/2)
        otoczka.set(jakwadrat)
        otoczka.set(otoczka.left-10,otoczka.top,otoczka.right+10,otoczka.bottom+10)
    }

    fun getPlayerRect():Rect{
        return otoczka
    }
}