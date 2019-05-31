package com.example.granateutatesa

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var manager:SensorManager? = null
    private var light_sensor:Sensor? = null

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_LIGHT){

            val light = event.values[0]

            Log.d("LIGHT_SENSOR", light.toString())

            if(light<20){
                startNewGame()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        manager = (this.getSystemService(Context.SENSOR_SERVICE)) as(SensorManager)
        light_sensor = manager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        manager!!.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_GAME)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRestart() {
        manager!!.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_GAME)
        super.onRestart()
    }


    override fun onStop() {
        manager!!.unregisterListener(this)
        super.onStop()
    }



    fun startNewGame(){
        manager!!.unregisterListener(this)
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("POINTS", 127)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            val wygrana = data!!.getBooleanExtra("WYNIK_GRY", false)
            when(wygrana){
                true -> wynikgry.text = "Brawo, wygrana !"
                false -> wynikgry.text = "Spróbuj jeszcze raz"
            }
            manager!!.registerListener(this, light_sensor, SensorManager.SENSOR_DELAY_GAME)

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
