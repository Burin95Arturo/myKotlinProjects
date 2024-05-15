package com.example.a2doparcialpetdatabase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.fragments.PetInfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppActivity : AppCompatActivity() {

    private lateinit var bottomNavView : BottomNavigationView
    private lateinit var navHostFragment : NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostApp) as NavHostFragment
        bottomNavView = findViewById(R.id.bottombar)
        NavigationUI.setupWithNavController(bottomNavView,navHostFragment.navController)
    }
}