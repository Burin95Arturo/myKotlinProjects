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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.FirebaseFirestoreLegacyRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    lateinit var v: View
    lateinit var btnRegisterNewUser : Button
    lateinit var btnRegisterBack : Button
    lateinit var edtRegisterEmail : EditText
    lateinit var edtRegisterPassword : EditText
    lateinit var edtRegisterConfirmPassword : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)

        btnRegisterNewUser = v.findViewById(R.id.btnRegisterNewUser)
        btnRegisterBack = v.findViewById(R.id.btnRegisterBack)
        edtRegisterEmail = v.findViewById(R.id.edtRegisterEmail)
        edtRegisterPassword = v.findViewById(R.id.edtRegisterPassword)
        edtRegisterConfirmPassword = v.findViewById(R.id.edtRegisterConfirmPassword)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        btnRegisterNewUser.setOnClickListener{
            if(edtRegisterEmail.length()>0 && edtRegisterPassword.length()>0 && edtRegisterConfirmPassword.length()>0){

                val s1 = edtRegisterPassword.text.toString()
                val s2 = edtRegisterConfirmPassword.text.toString()
                if(s1.equals(s2, true)){
                   viewModel.addNewEmail(edtRegisterEmail.text.toString())
                   viewModel.addNewPassword(edtRegisterPassword.text.toString())

                   viewModel.createAccount()

                    findNavController().navigateUp()
                    Log.d("Test", "New Register Success")
                }else{
                    Log.d("Test", "Couldn´t Register")
                }
            }else{
                Snackbar.make(v, "Nothing to register", Snackbar.LENGTH_SHORT).show()
            }
        }
        btnRegisterBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}