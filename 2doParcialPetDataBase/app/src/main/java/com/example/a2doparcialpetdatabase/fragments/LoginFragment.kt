package com.example.a2doparcialpetdatabase.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
        private const val TAG = "Test"
    }

    private lateinit var viewModel: LoginViewModel
    lateinit var v : View
    lateinit var btnLoginRegister : Button
    lateinit var edtLoginEmail : EditText
    lateinit var edtLoginPassword : EditText
    lateinit var btnLogin : Button
    lateinit var txtRegisterTextButton : TextView
    lateinit var txtRecoverPasswordButton : TextView

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    private fun updateUI(user: FirebaseUser?) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        btnLoginRegister = v.findViewById(R.id.btnLoginRegister)
        edtLoginEmail = v.findViewById(R.id.edtLoginEmail)
        edtLoginPassword = v.findViewById(R.id.edtLoginPassword)
        btnLogin = v.findViewById(R.id.btnLogin)
        txtRegisterTextButton = v.findViewById(R.id.txtRegisterTextButton)
        txtRecoverPasswordButton = v.findViewById(R.id.txtRecoverPasswordButton)

        // Initialize Firebase Auth
        auth = Firebase.auth

        return v

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d("Test", "show : ${currentUser.toString()}")

        if (currentUser != null) {
            // User is signed in
            Log.d("Test", "Loaded")
            //auth.signOut()
            db.collection("users").document(viewModel.newuser.uid).set(viewModel.newuser)
        } else {
            // No user is signed in
            Log.d("Test", "Not Loaded")
        }

        txtRegisterTextButton.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        txtRecoverPasswordButton.setOnClickListener{

            if(edtLoginEmail.length()>0){
                val emailAddress = edtLoginEmail.text.toString()
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent Successfully")
                            Snackbar.make(v, "Link Sent", Snackbar.LENGTH_SHORT).show()
                        }else{
                            Snackbar.make(v, "Something Failed", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener{
                        Snackbar.make(v, "Something Failed", Snackbar.LENGTH_SHORT).show()
                    }
            }else{
                Snackbar.make(v, "No Email to Reset", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnLogin.setOnClickListener{
            if(edtLoginEmail.length()>0 && edtLoginPassword.length()>0)
            {
                auth.signInWithEmailAndPassword(edtLoginEmail.text.toString(), edtLoginPassword.text.toString())
                    .addOnCompleteListener(){task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)

                            val action = LoginFragmentDirections.actionLoginFragmentToAppActivity()
                            v.findNavController().navigate(action)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            updateUI(null)
                        }
                    }
            }else{
                Snackbar.make(v, "Couldn't Login", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}