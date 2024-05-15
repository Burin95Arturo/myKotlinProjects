package com.example.a2doparcialpetdatabase.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.a2doparcialpetdatabase.entities.Users
import com.example.a2doparcialpetdatabase.fragments.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    private lateinit var email : String
    private lateinit var password : String
    val TAG = "EmailPassword"
    val newuser = Users()

    fun addNewEmail (text : String){
        email = text
    }

    fun addNewPassword (text : String){
        password = text
    }

    fun addNewUser(uid : String, name : String, password : String, email : String, image : String, age : Int){
        newuser.uid = uid
        newuser.name = name
        newuser.password = password
        newuser.email = email
        newuser.image = image
        newuser.age = age
    }

    fun getEmail(): String {
        return email
    }

    fun getPassword(): String {
        return password
    }

    fun createAccount(){//email: String, password: String) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(){task ->
            if(task.isSuccessful){
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user)
            }else{
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                /*Toast.makeText(context, "authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()*/
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {


            addNewUser(user.uid, "...", getPassword(), getEmail(), "...", 28)
            Log.d("Test", "User: ${newuser.email} uid: ${user.uid}")

            db.collection("users").document(user.uid)
                .set(newuser)
                .addOnSuccessListener {
                    Log.d("Test", "User added : ${newuser.name}")
                }
                .addOnFailureListener { e ->
                    Log.w("Test", "Error adding document", e)
                }
        }
    }
}