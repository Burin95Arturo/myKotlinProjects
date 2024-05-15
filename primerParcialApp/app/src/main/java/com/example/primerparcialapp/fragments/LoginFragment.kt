package com.example.primerparcialapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.motion.utils.ViewState
import androidx.core.view.isInvisible
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.primerparcialapp.R
import com.example.primerparcialapp.activities.LoginActivity.Companion.preferencias
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.userDao
import com.example.primerparcialapp.entities.User
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {


    lateinit var v :View

    private var db : appDatabase? = null
    private var userDao : userDao? = null

    lateinit var edtUserName: EditText
    lateinit var edtPassword : EditText
    lateinit var edtEmail : EditText
    lateinit var btnLogin : Button
    lateinit var btnRegister : Button
    lateinit var imgProfile : ImageView

    var defaultImage : String = "https://i.pinimg.com/564x/72/94/ea/7294ea3fba991841a7e092de34841896.jpg"

    lateinit var userList : MutableList<User>

    // Contador para el ID de los usuarios, la primary key
    var i : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        edtUserName = v.findViewById(R.id.edtUserName)
        edtPassword = v.findViewById(R.id.edtPassword)
        edtEmail = v.findViewById(R.id.edtEmail)
        btnLogin = v.findViewById(R.id.btnLogin)
        btnRegister = v.findViewById(R.id.btnRegister)
        imgProfile = v.findViewById(R.id.imgProfile)

        return v
    }

    override fun onStart() {
        super.onStart()

        val sett = PreferenceManager.getDefaultSharedPreferences(v.context)
        if(sett.getBoolean("cheers", false)){
            Snackbar.make(v, "WELCOME TO JACKSON", Snackbar.LENGTH_SHORT).show()
            //imgProfile.visibility = INVISIBLE
        }
        if(sett.getBoolean("hideimage", false)){
            imgProfile.visibility = INVISIBLE
        }else{
            imgProfile.visibility = VISIBLE
        }

        Glide.with(v).load(defaultImage).into(imgProfile)
        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        btnLogin.setOnClickListener{

            userList = userDao?.loadAllPersons() as MutableList<User>

            //Busco si está el usuario
            for ( actualUser in userList){

                if(edtUserName.text.toString() == actualUser.name){
                    val action = LoginFragmentDirections.actionLoginFragmentToAppActivity()
                    preferencias.saveInt(actualUser.id)
                    Log.d("TEST", actualUser.id.toString())
                    if(edtEmail.text.toString() == actualUser.email){

                        if(edtPassword.text.toString() == actualUser.password){
                            v.findNavController().navigate(action)
                        }else{
                            Snackbar.make(v, "PASSWORD DENIED", Snackbar.LENGTH_SHORT).show()
                        }
                    }else{
                        Snackbar.make(v, "EMAIL DENIED", Snackbar.LENGTH_SHORT).show()
                    }
                }else
                {
                    Snackbar.make(v, "USER DENIED", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        btnRegister.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            v.findNavController().navigate(action)
        }
    }

}