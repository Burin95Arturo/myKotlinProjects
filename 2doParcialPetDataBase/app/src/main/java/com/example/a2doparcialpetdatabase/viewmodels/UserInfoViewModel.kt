package com.example.a2doparcialpetdatabase.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.a2doparcialpetdatabase.entities.Pets
import com.example.a2doparcialpetdatabase.entities.Users
import com.example.a2doparcialpetdatabase.fragments.UserInfoFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.UUID

class UserInfoViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    val userList : MutableList<Users> = ArrayList<Users>()
    val myUser = MutableLiveData<Users?>()
    val status = MutableLiveData<Boolean?>()
    val status2 = MutableLiveData<Boolean?>()

    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    fun setUserInfo(currentUser: FirebaseUser) {
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val userRef = db.collection("users")
                val userRefList = userRef.document(currentUser.uid).get().await()
                if(userRefList.exists()){
                    val pepito = userRefList.toObject(Users::class.java)
                    if (pepito != null) {
                        //Log.d( "Test", "${pepito.email}")
                        myUser.postValue(pepito)
                    }
                }else{
                    Log.d( "Test", "No hay nada")
                }
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }

    fun deleteUser2(currentUser: FirebaseUser?) {
        auth = Firebase.auth
        viewModelScope.launch {
            try {
                currentUser!!.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Test", "User account deleted.")
                            auth.signOut()
                            status.value = true
                        }else{
                            Log.d("Test", "User account deleted failed.")
                            status.value = false
                        }
                    }
                    .addOnFailureListener{
                        Log.d("Test", "Error deleted failed.")
                    }
            } catch (e: Exception) {
                Log.d("Test", "${e.message}")
            }
        }
    }

    fun deleteUser(currentUser: FirebaseUser?) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                // Perform the delete operation in the background thread using await()
                //currentUser?.let {
                if (currentUser != null) {
                    currentUser.delete().await()
                    Log.d("Test", "User account deleted.")
                    status.postValue(true)
                }else{
                    status.postValue(false)
                }
            } catch (e: Exception) {
                Log.d("Test", "User account deletion failed: ${e.message}")
                //status.postValue(false)
            }
        }
    }

    fun changeUserInfo(name: String, age: String, surname: String, currentUser: FirebaseUser?) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val userRef = db.collection("users")
                val userRefList = userRef.document(currentUser!!.uid).get().await()

                if(userRefList.exists()){
                    val pepito = userRefList.toObject(Users::class.java)
                    if (pepito != null) {
                        //Log.d( "Test", "${pepito.email}")
                       // myUser.postValue(pepito)
                        pepito.name = name
                        pepito.surname = surname
                        pepito.age = age.toInt()
                        db.collection("users").document(currentUser!!.uid).set(pepito).await()
                        myUser.postValue(pepito)
                        status2.postValue(true)
                    }
                }else{
                    Log.d( "Test", "No hay nada")
                    status2.postValue(false)
                }

            } catch (e: Exception) {
                Log.d("Test", "Error: ${e.message}")
                //status.postValue(false)
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

    fun editUserImage(uid: String, downloadUrl: String) {
        viewModelScope.launch {
            try {
                // Reference to the Firestore collection
                val actualUserRef = db.collection("users").document(uid)
                val _actualUser = actualUserRef.get().await()
                if(_actualUser.exists()){
                    val actualUser = _actualUser.toObject(Users::class.java)
                    if (actualUser != null) {
                        actualUser.image = downloadUrl
                        actualUserRef.set(actualUser).await()
                        myUser.postValue(actualUser)
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