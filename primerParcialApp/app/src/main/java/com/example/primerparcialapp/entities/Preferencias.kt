package com.example.primerparcialapp.entities

import android.content.Context

class Preferencias (val context: Context){

    val SHARED_NAME = "UserId"
    val SHARED_USER_NAME = "UserName"
    val SHARED_INT = "vip"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)
    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }
    fun saveInt(vip:Int){
        storage.edit().putInt(SHARED_INT, vip).apply()
    }

    fun getName():String{
        return storage.getString(SHARED_USER_NAME,"")!!
    }

    fun getInt():Int{
        return storage.getInt(SHARED_INT, 0)
    }
}