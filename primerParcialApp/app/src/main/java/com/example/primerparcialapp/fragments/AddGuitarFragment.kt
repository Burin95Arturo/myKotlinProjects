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
import androidx.navigation.fragment.navArgs
import com.example.primerparcialapp.R
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.guitarDao
import com.example.primerparcialapp.entities.Guitarra
import com.google.android.material.snackbar.Snackbar

class AddGuitarFragment : Fragment() {

    lateinit var v : View
    lateinit var btnAddGuitar : Button
    lateinit var btnAddBack : Button
    lateinit var edtAddName : EditText
    lateinit var edtAddFamily : EditText
    lateinit var edtAddBodyMaterial : EditText
    lateinit var edtAddMastilMaterial : EditText
    lateinit var edtAddCountry : EditText
    lateinit var edtAddImageURL : EditText
    lateinit var edtAddAmountStrings : EditText

    var defaultImage : String = "https://i.pinimg.com/564x/10/c9/9e/10c99e38d1659fbefff072103df1d4de.jpg"

    private var guitarDao : guitarDao? = null
    private var db: appDatabase? = null
    private var actualGuitar : Guitarra? = null
    // Lo siguiente es clave para pasar parcelables entre fragments
    private val args: AppDetailedFragmentArgs by navArgs()

    var guitarList : MutableList<Guitarra> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_add_guitar, container, false)

        btnAddBack = v.findViewById(R.id.btnEditBack)
        btnAddGuitar = v.findViewById(R.id.btnEditSave)

        edtAddCountry = v.findViewById(R.id.edtEditCountry)
        edtAddFamily = v.findViewById(R.id.edtEditFamily)
        edtAddName = v.findViewById(R.id.edtEditName)
        edtAddBodyMaterial = v.findViewById(R.id.edtEditBodyMaterial)
        edtAddMastilMaterial = v.findViewById(R.id.edtEditMastilMaterial)
        edtAddImageURL = v.findViewById(R.id.edtEditImageURL)
        edtAddAmountStrings = v.findViewById(R.id.edtEditAmountStrings)

        return v
    }

    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        guitarDao = db?.guitarDao()
        guitarList = guitarDao?.loadAllGuitar() as MutableList<Guitarra>

        var aux = guitarList.size
        //var auxLast = guitarDao?.loadGuitarById(args.id)
        //Log.d("TEST", "valor: ${auxLast.id}")


        btnAddGuitar.setOnClickListener {
            var cuerdas = edtAddAmountStrings.text.toString().toInt()
            Log.d("TEST", "cuerdas: ${cuerdas}")

            var newGuitar = Guitarra(aux + 1,edtAddName.text.toString(), "Jackson", defaultImage,
                edtAddFamily.text.toString(), edtAddBodyMaterial.text.toString(), edtAddMastilMaterial.text.toString(),
                cuerdas, edtAddCountry.text.toString() )

            if(edtAddName.length()>0){
                guitarDao?.insertGuitar(newGuitar)
                v.findNavController().navigateUp()
            }else{
                Snackbar.make(v, "Nothing to add", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnAddBack.setOnClickListener{
            v.findNavController().navigateUp()
        }
    }
}