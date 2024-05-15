package com.example.primerparcialapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primerparcialapp.R
import com.example.primerparcialapp.entities.Preferencias

class LoginActivity : AppCompatActivity() {

    companion object{
        lateinit var preferencias:Preferencias
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferencias = Preferencias(applicationContext)
    }

}