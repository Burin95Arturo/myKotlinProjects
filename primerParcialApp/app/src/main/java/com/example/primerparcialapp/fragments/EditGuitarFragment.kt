package com.example.primerparcialapp.fragments

import androidx.lifecycle.ViewModelProvider
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

class EditGuitarFragment : Fragment() {

    lateinit var v : View
    lateinit var btnEditSave : Button
    lateinit var btnEditBack : Button
    lateinit var edtEditName : EditText
    lateinit var edtEditFamily : EditText
    lateinit var edtEditBodyMaterial : EditText
    lateinit var edtEditMastilMaterial : EditText
    lateinit var edtEditCountry : EditText
    lateinit var edtEditImageURL : EditText
    lateinit var edtEditAmountStrings : EditText

    private var guitarDao : guitarDao? = null
    private var db: appDatabase? = null
    private var actualGuitar : Guitarra? = null
    private val args: AppDetailedFragmentArgs by navArgs()

    var guitarList : MutableList<Guitarra> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_guitar, container, false)

        btnEditBack = v.findViewById(R.id.btnEditBack)
        btnEditSave = v.findViewById(R.id.btnEditSave)

        edtEditCountry = v.findViewById(R.id.edtEditCountry)
        edtEditFamily = v.findViewById(R.id.edtEditFamily)
        edtEditName = v.findViewById(R.id.edtEditName)
        edtEditBodyMaterial = v.findViewById(R.id.edtEditBodyMaterial)
        edtEditMastilMaterial = v.findViewById(R.id.edtEditMastilMaterial)
        edtEditImageURL = v.findViewById(R.id.edtEditImageURL)
        edtEditAmountStrings = v.findViewById(R.id.edtEditAmountStrings)

        return v
    }


    override fun onStart() {
        super.onStart()
        Log.d("TEST", "Entro a EditGuitar")
        db = appDatabase.getAppDataBase(v.context)
        guitarDao = db?.guitarDao()
        //guitarList = guitarDao?.loadAllGuitar() as MutableList<Guitarra>

        actualGuitar = guitarDao?.loadGuitarById(args.id)
        //edtEditAmountStrings.setText(actualGuitar?.amountStrings.toString())
        btnEditBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        edtEditCountry.setText(actualGuitar?.country)
        edtEditFamily.setText(actualGuitar?.family)
        edtEditName.setText(actualGuitar?.name)
        edtEditBodyMaterial.setText(actualGuitar?.materialBody)
        edtEditMastilMaterial.setText(actualGuitar?.materialMastil)
        edtEditImageURL.setText(actualGuitar?.imageUrl)
        edtEditAmountStrings.setText(actualGuitar?.amountStrings.toString())

        btnEditSave.setOnClickListener {
            if(edtEditCountry.length() > 0){
                if(edtEditFamily.length() > 0){
                    if(edtEditName.length() > 0){
                        if(edtEditBodyMaterial.length() > 0){
                            if(edtEditMastilMaterial.length() > 0){
                                if(edtEditMastilMaterial.length() > 0){
                                    if(edtEditImageURL.length() > 0){
                                        guitarDao?.updateGuitar(Guitarra(args.id,
                                            edtEditName.text.toString(), "Jackson", edtEditImageURL.text.toString(),
                                            edtEditFamily.text.toString(),edtEditBodyMaterial.text.toString(),
                                            edtEditMastilMaterial.text.toString(), edtEditAmountStrings.text.toString().toInt(),
                                            edtEditCountry.toString()))
                                        v.findNavController().navigateUp()
                                    }else{
                                        Snackbar.make(v, "FEW ARGUMENTS", Snackbar.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}