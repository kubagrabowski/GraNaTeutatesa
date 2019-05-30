package com.example.granateutatesa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onStartClick(view: View){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("POINTS", 10)
        startActivity(intent)
    }
}
