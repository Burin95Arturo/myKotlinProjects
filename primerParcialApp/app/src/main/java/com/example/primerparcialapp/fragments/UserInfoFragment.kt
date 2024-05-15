package com.example.primerparcialapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.primerparcialapp.R
import com.example.primerparcialapp.activities.LoginActivity
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.userDao
import com.example.primerparcialapp.entities.User

class UserInfoFragment : Fragment() {

    lateinit var v : View

    private var db : appDatabase? = null
    private var userDao : userDao? = null

    lateinit var userList : MutableList<User>
    lateinit var actualUser : User

    lateinit var btnEdit : Button
    lateinit var btnSettings : Button
    lateinit var txtInfoName : TextView
    lateinit var txtInfoEmail : TextView
    lateinit var txtInfoAge : TextView
    lateinit var imgInfo : ImageView

    // Lo siguiente es clave para pasar parcelables entre fragments
    //private val args: UserInfoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_info, container, false)

        btnEdit = v.findViewById(R.id.btnEdit)
        btnSettings = v.findViewById((R.id.btnSettings))
        txtInfoAge = v.findViewById(R.id.txtInfoAge)
        txtInfoEmail = v.findViewById(R.id.txtInfoEmail)
        txtInfoName = v.findViewById(R.id.txtInfoName)
        imgInfo = v.findViewById(R.id.imgInfo)

        return v
    }
    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        var value = LoginActivity.preferencias.getInt()
        Log.d("TEST", value.toString())

        actualUser = userDao?.loadPersonById(value) as User

        txtInfoName.text = actualUser.name
        txtInfoEmail.text = actualUser.email
        txtInfoAge.text = actualUser.age
        Glide.with(v).load(actualUser.image).into(imgInfo)

        btnSettings.setOnClickListener{
            val action = UserInfoFragmentDirections.actionUserInfoFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }

        btnEdit.setOnClickListener{
            val action = UserInfoFragmentDirections.actionUserInfoFragmentToEditUserInfoFragment()
            v.findNavController().navigate(action)
        }
    }
}