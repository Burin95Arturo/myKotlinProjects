package com.example.primerparcialapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.primerparcialapp.entities.User

@Dao
public interface userDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllPersons(): MutableList<User?>?

    //Acá especifico el usuario que quiero traer segun la id
    @Query("SELECT * FROM users WHERE id = :id")
    fun loadPersonById(id : Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user : User?)

    @Update
    fun updatePerson(user: User?)

    @Delete
    fun delete(user: User?)

}