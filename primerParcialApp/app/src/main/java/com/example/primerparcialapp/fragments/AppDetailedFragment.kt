package com.example.primerparcialapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.primerparcialapp.R
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.guitarDao
import com.example.primerparcialapp.entities.Guitarra
import org.w3c.dom.Text
import java.util.concurrent.atomic.DoubleAdder

class AppDetailedFragment : Fragment() {

    lateinit var v : View

    lateinit var btnAppDetailBack : Button
    lateinit var btnDetailDelete : Button
    lateinit var btnDetailEdit : Button
    lateinit var txtDetailName: TextView
    lateinit var txtDetailBrand : TextView
    lateinit var txtDetailMaterialBody : TextView
    lateinit var txtDetailMaterialMastil : TextView
    lateinit var txtDetailAmountStrings : TextView

    private var guitarDao : guitarDao? = null
    private var db: appDatabase? = null

    private var actualGuitar : Guitarra? = null

    // Lo siguiente es clave para pasar parcelables entre fragments
    private val args: AppDetailedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_app_detailed, container, false)

        btnAppDetailBack = v.findViewById(R.id.btnDetailBack)
        btnDetailEdit = v.findViewById(R.id.btnDetailEdit)
        btnDetailDelete = v.findViewById(R.id.btnDetailDelete)
        txtDetailBrand= v.findViewById(R.id.txtDetailBrand)
        txtDetailAmountStrings = v.findViewById(R.id.txtDetailAmountStrings)
        txtDetailName = v.findViewById(R.id.txtDetailName)
        txtDetailMaterialBody = v.findViewById(R.id.txtDetailMaterialBody)
        txtDetailMaterialMastil = v.findViewById(R.id.txtDetailMaterialMastil)

        return v
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST", "Ingreso a fragment App\n")

        db = appDatabase.getAppDataBase(v.context)
        guitarDao = db?.guitarDao()

        actualGuitar = guitarDao?.loadGuitarById(args.id)

        txtDetailBrand.text = actualGuitar?.brand.toString()
        txtDetailAmountStrings.text = actualGuitar?.amountStrings.toString()
        txtDetailName.text = actualGuitar?.name.toString()
        txtDetailMaterialBody.text = actualGuitar?.materialBody.toString()
        txtDetailMaterialMastil.text = actualGuitar?.materialMastil.toString()

        btnAppDetailBack.setOnClickListener{
            v.findNavController().navigateUp()
        }

        btnDetailDelete.setOnClickListener{
            Log.d("TEST", "id ${args.id}")
            var guitarToDelete = guitarDao?.loadGuitarById(args.id)
            guitarDao?.delete(guitarToDelete)
            v.findNavController().navigateUp()
        }

        btnDetailEdit.setOnClickListener{
            val action = AppDetailedFragmentDirections.actionAppDetailedFragmentToEditGuitarFragment(args.id)
            v.findNavController().navigate(action)
        }
    }
}