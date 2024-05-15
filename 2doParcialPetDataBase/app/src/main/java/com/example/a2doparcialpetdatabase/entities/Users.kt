package com.example.a2doparcialpetdatabase.entities

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class Users (uid : String, name : String, password : String, email : String, imageurl : String, age : Int, surname : String)
{

    constructor() : this("", "", "", "", "", 0, "")
    {

    }

    @ColumnInfo(name = "uid")
    var uid : String

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "password")
    var password : String

    @ColumnInfo(name = "email")
    var email : String

    @ColumnInfo(name = "image")
    var image : String

    @ColumnInfo(name = "age")
    var age : Int

    @ColumnInfo(name = "surname")
    var surname : String


    init {
        this.uid = uid
        this.name = name
        this.password = password
        this.email = email
        this.image = imageurl
        this.age = age
        this.surname = surname
    }

}