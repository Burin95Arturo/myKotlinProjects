package com.example.a2doparcialpetdatabase.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.a2doparcialpetdatabase.entities.Pets

class AddPetViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var pets = Pets()

    fun setPet(uid : String, name : String, breed : String, urlimage : String, notes :String, age : Int, owner : String, owneremail : String) {
        pets = Pets(uid, name, breed, urlimage, notes, age, owner, owneremail)
    }

    fun getPet() : Pets {
        return pets
    }

    fun showLastPet() {
        Log.d("Test", "${pets.name}")
    }
}