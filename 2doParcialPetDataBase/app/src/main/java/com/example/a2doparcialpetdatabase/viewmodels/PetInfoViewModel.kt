package com.example.a2doparcialpetdatabase.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2doparcialpetdatabase.entities.Pets
import com.example.a2doparcialpetdatabase.entities.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.UUID

class PetInfoViewModel : ViewModel() {


    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    val pets = MutableLiveData<Boolean?>()
    val myPets = MutableLiveData<Pets?>()
    val status = MutableLiveData<Boolean?>()
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    fun editPets(name: String, age: String, breed: String, notes: String, uid: String) {
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val actualPetRef = db.collection("pets").document(uid)
                val _actualPet = actualPetRef.get().await()
                if(_actualPet.exists()){
                    val actualPet = _actualPet.toObject(Pets::class.java)
                    if (actualPet != null) {
                        //Log.d( "Test", "${pepito.email}")
                        actualPet.name = name
                        actualPet.age = age.toInt()
                        actualPet.breed = breed
                        actualPet.notes = notes

                        actualPetRef.set(actualPet).await()
                        myPets.postValue(actualPet)
                    }
                }else{
                    Log.d( "Test", "No hay nada")
                }
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }

    fun deletePets(uid: String) {
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val actualPetRef = db.collection("pets").document(uid)
                val _actualPet = actualPetRef.get().await()
                if(_actualPet.exists()){
                    actualPetRef.delete().await()
                    status.postValue(true)
                }else{
                    Log.d( "Test", "No hay nada")
                    status.postValue(false)
                }
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }

    fun uploadImage(bitmap: Bitmap, storagePath: String, onSuccess: (String) -> Unit, onFailure: () -> Unit) {
        // Convert Bitmap to byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()

        // Generate a unique filename for the image
        val filename = UUID.randomUUID().toString()

        // Create a reference to the specified path in Firebase Storage
        val imageRef = storageReference.child("$storagePath/$filename.jpg")

        // Upload the byte array to Firebase Storage
        imageRef.putBytes(data)
            .addOnSuccessListener {
                // Get the download URL after successful upload
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString()) // Pass the download URL to the callback
                }
            }
            .addOnFailureListener {
                Log.e("FirebaseStorageHelper", "Image upload failed: ${it.message}")
                onFailure() // Notify the callback of failure
            }
    }

    fun editPetImage(uid: String, downloadUrl: String) {
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val actualPetRef = db.collection("pets").document(uid)
                val _actualPet = actualPetRef.get().await()
                if(_actualPet.exists()){
                    val actualPet = _actualPet.toObject(Pets::class.java)
                    if (actualPet != null) {
                        actualPet.imageUrl = downloadUrl
                        actualPetRef.set(actualPet).await()
                        myPets.postValue(actualPet)
                    }
                }else{
                    Log.d( "Test", "No hay nada")
                }
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }


}