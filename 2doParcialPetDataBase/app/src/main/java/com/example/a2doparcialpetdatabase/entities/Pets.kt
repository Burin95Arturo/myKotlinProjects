package com.example.a2doparcialpetdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
class Pets (uid : String, name: String, breed : String,
            imageurl: String, notes: String, age : Int,
            owner : String, owneremail : String)
{

    constructor() : this("", "", "", "", "", 0, "", "")
    {

    }

    @ColumnInfo(name = "uid")
    var uid : String

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "breed")
    var breed : String

    @ColumnInfo(name = "imageurl")
    var imageUrl : String

    @ColumnInfo(name = "notes")
    var notes : String

    @ColumnInfo(name = "age")
    var age : Int

    @ColumnInfo(name = "owner")
    var owner : String

    @ColumnInfo(name = "owneremail")
    var owneremail : String

    init {
        this.uid = uid
        this.name = name
        this.breed = breed
        this.imageUrl = imageurl
        this.notes  = notes
        this.age = age
        this.owner = owner
        this.owneremail = owneremail
    }
}