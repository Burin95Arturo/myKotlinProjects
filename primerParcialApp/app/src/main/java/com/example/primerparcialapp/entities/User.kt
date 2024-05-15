package com.example.primerparcialapp.entities

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

// Serializar para convertir en String, como el JSON

@Entity(tableName = "users")
//@Parcelize
class User ( id : Int, name : String, password : String, email : String, image : String, age : String){

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "password")
    var password : String

    @ColumnInfo(name = "email")
    var email : String

    @ColumnInfo(name = "image")
    var image : String

    @ColumnInfo(name = "age")
    var age : String

    init {
        this.id = id
        this.name = name
        this.password = password
        this.email = email
        this.image = image
        this.age = age
    }


    // Para mostrar el args.user.toString() cuando hago un Log.d
    // O implementar la clase como data class User ( etc etc )
    override fun toString(): String {
        return "User(name='$name', email='$email', password='$password', image='$image')"
    }
}