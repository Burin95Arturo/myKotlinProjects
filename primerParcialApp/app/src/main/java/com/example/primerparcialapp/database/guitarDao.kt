package com.example.primerparcialapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.primerparcialapp.entities.Guitarra

@Dao
public interface guitarDao {
    @Query("SELECT * FROM guitars ORDER BY id")     // Ordeno por Id
    fun loadAllGuitar(): MutableList<Guitarra?>?   // Signo de pregunta significa que el dato puede ser NULO, porque una query puede devolver NULO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuitar(guitar: Guitarra?)

    @Update
    fun updateGuitar(guitar: Guitarra?)

    @Delete
    fun delete(guitar: Guitarra?)

    @Query("SELECT * FROM guitars WHERE id = :id")
    fun loadGuitarById(id: Int): Guitarra?
}

