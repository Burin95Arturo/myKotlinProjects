package com.example.primerparcialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent
import com.example.primerparcialapp.activities.LoginActivity

class MainActivity : AppCompatActivity() {

    //MainActivity as a Splashscreen activity
    private val SPLASH_TIME_OUT:Long = 2000 // 3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(

            {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            , SPLASH_TIME_OUT)

    }
}