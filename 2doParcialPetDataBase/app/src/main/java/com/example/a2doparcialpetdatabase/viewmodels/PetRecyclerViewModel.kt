package com.example.a2doparcialpetdatabase.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2doparcialpetdatabase.entities.Pets
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirestoreRegistrar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PetRecyclerViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    var pets : MutableList<Pets> = ArrayList<Pets>()
    var petList : MutableList<Pets> = ArrayList<Pets>()
    var urlimage = "https://i.pinimg.com/236x/1f/d4/d4/1fd4d4a098adf04fc6f97c96b79969d2.jpg"

    private val firestore = FirebaseFirestore.getInstance()

    // MutableLiveData for the list of documents in the collection
    private var _documentList = MutableLiveData<MutableList<Pets>>()

    val documentList : LiveData<MutableList<Pets>>
        get() = _documentList

    fun getPets(){
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val collection = firestore.collection("pets")

                // Fetch documents from the collection
                val documents = collection.get().await()

                // Map the documents to your Pets data model
                val petsList = documents.toObjects(Pets::class.java)

                // Update the LiveData with the fetched data
                _documentList.value = petsList.toMutableList()
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }

    fun initTestList ()
    {
        pets.add(Pets("" ,"Chunis","Caniche", urlimage,"Fox Terrier",7,"mascotas.com","blabla@gmail.com"))
        pets.add(Pets("" ,"Chunis","Caniche", urlimage,"Gran Danes",7,"mascotas.com","blabla@gmail.com"))
        pets.add(Pets("" ,"Chunis","Caniche",urlimage,"Siames",7,"mascotas.com", "blabla@gmail.com"))
        pets.add(Pets("" ,"Chunis","Caniche",urlimage,"Pardo",7,"mascotas.com","blabla@gmail.com"))
        pets.add(Pets("" ,"Chunis","Caniche",urlimage,"Arlequin",7,"mascotas.com","blabla@gmail.com"))
    }

    fun getList() : MutableList<Pets>{
        return pets
    }

    fun addToList(uid : String, name : String, breed : String, urlimage : String, notes :String, age : Int, owner : String, owneremail : String) {
        pets.add(Pets(uid, name, breed, urlimage, notes, age, owner, owneremail))
    }

    fun addObjectToList(new : Pets){
        pets.add(new)
    }
}