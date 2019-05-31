package com.example.granateutatesa

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class Player(private val jakwadrat:Rect, private val context:Context):GameObject,SensorEventListener {

    private val otoczka = Rect(jakwadrat)

    private val manager = (context.getSystemService(Context.SENSOR_SERVICE)) as(SensorManager)
    private val sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var rotation = 0.0f
    private val speedwhenRot = 12.0f

    init {
        register()
    }

    private fun register(){
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stopSensor(){
        manager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            rotation = event.values[0]
            Log.d("SENSORX", rotation.toString())
        }
    }

    override fun draw(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.chest)
        canvas.drawBitmap(bitmap,null, otoczka,Paint())

    }

    override fun update() {
        if(-0.7f < rotation && rotation < 0.7f ){
            rotation=0.0f
        }
        Log.d("ROT", (speedwhenRot*rotation).toString())
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

    /*fun update(point:Point){
        jakwadrat.set(point.x-jakwadrat.width()/2, point.y - jakwadrat.height()/2, point.x + jakwadrat.width()/2, point.y + jakwadrat.height()/2)
        otoczka.set(jakwadrat)
        otoczka.set(otoczka.left-10,otoczka.top,otoczka.right+10,otoczka.bottom+10)
    }*/

    fun getPlayerRect():Rect{

        return otoczka
    }
}