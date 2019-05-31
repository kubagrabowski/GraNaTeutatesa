package com.example.granateutatesa

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager

class GameActivity : Activity() {

    var actualPanel:GamePanel? = null

    override fun onBackPressed() {
        actualPanel!!.player!!.stopSensor()
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        GamePanel.SCREEN_HEIGHT = dm.heightPixels
        GamePanel.SCREEN_WIDTH = dm.widthPixels

        actualPanel = GamePanel(this,this, this.intent.getIntExtra("POINTS",1))
        setContentView(actualPanel)
        //setContentView(GamePanel(this,this, this.intent.getIntExtra("POINTS",1)))
    }

    fun end(wygrana:Boolean){

        val intent = this.intent
        intent.putExtra("WYNIK_GRY",wygrana)
        setResult(Activity.RESULT_OK,intent)

        finish()
    }

}
