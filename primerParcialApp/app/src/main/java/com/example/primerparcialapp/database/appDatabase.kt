package com.example.primerparcialapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.primerparcialapp.entities.Guitarra
import com.example.primerparcialapp.entities.User


@Database(entities = [User::class, Guitarra::class], version = 1, exportSchema = false)

public  abstract class appDatabase : RoomDatabase() {

    abstract fun userDao(): userDao
    abstract fun guitarDao(): guitarDao

    companion object {
        var INSTANCE: appDatabase? = null

        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDatabase::class.java,
                        "myDB"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}