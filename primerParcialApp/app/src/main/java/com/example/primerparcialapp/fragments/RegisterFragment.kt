package com.example.primerparcialapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.primerparcialapp.R
import com.example.primerparcialapp.activities.LoginActivity.Companion.preferencias
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.userDao
import com.example.primerparcialapp.entities.User
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    lateinit var v : View

    private var db : appDatabase? = null
    private var userDao : userDao? = null

    lateinit var edtNewUserName : EditText
    lateinit var edtNewEmail : EditText
    lateinit var edtNewPassword : EditText
    lateinit var btnBack : Button
    lateinit var btnNewRegister : Button

    var defaultImage : String = "https://i.pinimg.com/564x/72/94/ea/7294ea3fba991841a7e092de34841896.jpg"

    lateinit var userList : MutableList<User>

    // Contador para el ID de los usuarios, la primary key
    var i : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)

        edtNewEmail = v.findViewById(R.id.edtNewEmail)
        edtNewPassword = v.findViewById(R.id.edtNewPassword)
        edtNewUserName = v.findViewById(R.id.edtNewUserName)
        btnBack = v.findViewById(R.id.btnBack)
        btnNewRegister = v.findViewById(R.id.btnNewRegister)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        userList = userDao?.loadAllPersons() as MutableList<User>

        btnBack.setOnClickListener{
            v.findNavController().navigateUp()
        }
        for (actualUser in userList){
            i += 1
        }
        if(i>0){ //tengo usuarios registrados
            btnNewRegister.setOnClickListener{
                if(edtNewUserName.length()>0 && edtNewEmail.length()>0 && edtNewPassword.length()>0){

                    userDao?.insertPerson(User(
                        i,
                        edtNewUserName.text.toString(),
                        edtNewPassword.text.toString(),
                        edtNewEmail.text.toString(),
                        defaultImage,
                        "28"
                    ))

                    v.findNavController().navigateUp()

                }else{
                    Snackbar.make(v, "INFO INCOMPLETE", Snackbar.LENGTH_SHORT).show()
                }
            }
        }else{  //no tengo usuarios registrados
            btnNewRegister.setOnClickListener{
                if(edtNewUserName.length()>0 && edtNewEmail.length()>0 && edtNewPassword.length()>0){

                    userDao?.insertPerson(User(
                        0,
                        edtNewUserName.text.toString(),
                        edtNewPassword.text.toString(),
                        edtNewEmail.text.toString(),
                        defaultImage,
                        "28"
                    ))

                    v.findNavController().navigateUp()

                }else{
                    Snackbar.make(v, "INFO INCOMPLETE", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}