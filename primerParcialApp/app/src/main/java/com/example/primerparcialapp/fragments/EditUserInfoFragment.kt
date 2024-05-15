package com.example.primerparcialapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.primerparcialapp.R
import com.example.primerparcialapp.activities.LoginActivity
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.userDao
import com.example.primerparcialapp.entities.User
import com.google.android.material.snackbar.Snackbar

class EditUserInfoFragment : Fragment() {

    lateinit var v : View

    private var db : appDatabase? = null
    private var userDao : userDao? = null
    lateinit var actualUser : User

    lateinit var btnEditInfoBack : Button
    lateinit var btnEditInfoSave : Button
    lateinit var btnEditInfoDelete : Button
    lateinit var btnEditLogOut : Button
    lateinit var edtEditUserName: EditText
    lateinit var edtEditUserEmail : EditText
    lateinit var edtEditUserAge : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_user_info, container, false)

        btnEditInfoBack = v.findViewById(R.id.btnEditInfoBack)
        btnEditInfoDelete = v.findViewById(R.id.btnEditInfoDelete)
        btnEditInfoSave = v.findViewById(R.id.btnEditInfoSave)
        btnEditLogOut = v.findViewById(R.id.btnEditLogOut)
        edtEditUserAge = v.findViewById(R.id.edtEditUserAge)
        edtEditUserEmail = v.findViewById(R.id.edtEditUserEmail)
        edtEditUserName = v.findViewById(R.id.edtEditUserName)

        return v
    }

    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        var value = LoginActivity.preferencias.getInt()
        Log.d("TEST", value.toString())

        actualUser = userDao?.loadPersonById(value) as User

        edtEditUserEmail.setText(actualUser.email)
        edtEditUserName.setText(actualUser.name)
        edtEditUserAge.setText(actualUser.age)

        btnEditInfoBack.setOnClickListener{
            v.findNavController().navigateUp()
        }

        btnEditInfoDelete.setOnClickListener{
            userDao?.delete(actualUser)
            // Deberia salir al activity login
            var action = EditUserInfoFragmentDirections.actionEditUserInfoFragmentToLoginActivity()
            v.findNavController().navigate(action)
        }

        btnEditInfoSave.setOnClickListener {

            if(edtEditUserAge.length()>0 && edtEditUserEmail.length()>0 && edtEditUserName.length()>0){

                userDao?.updatePerson(User(
                    value,
                    edtEditUserName.text.toString(),
                    actualUser.password,
                    edtEditUserEmail.text.toString(),
                    actualUser.image,
                    edtEditUserAge.text.toString()))
                v.findNavController().navigateUp()
            }else{
                Snackbar.make(v, "INFO INCOMPLETE", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnEditLogOut.setOnClickListener {
            var action = EditUserInfoFragmentDirections.actionEditUserInfoFragmentToLoginActivity()
            v.findNavController().navigate(action)
        }
    }
}