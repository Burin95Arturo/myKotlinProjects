package com.example.primerparcialapp.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "guitars")
class Guitarra ( id: Int, name: String, brand : String,
                 imageUrl: String, family: String, materialBody : String,
                 materialMastil : String, amountStrings : Int, country : String){

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int

    @ColumnInfo(name = "name")
    var name : String

    @ColumnInfo(name = "brand")
    var brand : String

    @ColumnInfo(name = "imageUrl")
    var imageUrl : String

    @ColumnInfo(name = "family")
    var family : String

    @ColumnInfo(name = "materialBody")
    var materialBody : String

    @ColumnInfo(name = "materialMastil")
    var materialMastil : String

    @ColumnInfo(name = "amountStrings")
    var amountStrings : Int

    @ColumnInfo(name = "country")
    var country : String

    init {
        this.id = id
        this.name = name
        this.brand = brand
        this.imageUrl = imageUrl
        this.family = family
        this.materialBody = materialBody
        this.materialMastil = materialMastil
        this.amountStrings = amountStrings
        this.country = country
    }

}
/*

@Parcelize
class Guitarra (
    var id: String,
    var name: String,
    var brand : String,
    var imageUrl: String,
    var family: String,
    var material_body : String,
    var material_mastil : String,
    var amount_strings : String,
    var country : String
    ) : Parcelable {
}
*/