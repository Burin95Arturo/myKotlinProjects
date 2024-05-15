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
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.viewmodels.PetMoreInfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.a2doparcialpetdatabase.entities.Pets

class PetMoreInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PetMoreInfoFragment()
    }

    lateinit var v  :View
    lateinit var btnPetMoreInfoBack : Button
    lateinit var edtPetMoreInfoOwnerName : EditText
    lateinit var edtPetMoreInfoOwnerPhone : EditText

    private lateinit var viewModel: PetMoreInfoViewModel

    val db = FirebaseFirestore.getInstance()
    private val args: PetMoreInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pet_more_info, container, false)
        btnPetMoreInfoBack = v.findViewById((R.id.btnPetMoreInfoBack))
        edtPetMoreInfoOwnerName = v.findViewById(R.id.edtPetMoreInfoOwnerName)
        edtPetMoreInfoOwnerPhone = v.findViewById(R.id.edtPetMoreInfoOwnerPhone)
        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PetMoreInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        try{
            db.collection("pets").document(args.id)
                .get()
                .addOnSuccessListener { document ->
                    Log.w("Test", "Reading success")
                    if(document != null)
                    {
                        val actualpet = document.toObject(Pets::class.java)!!
                        edtPetMoreInfoOwnerPhone.setText((actualpet.owneremail))
                        edtPetMoreInfoOwnerName.setText(actualpet.owner)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Test", "Error reading", e)
                }
        }catch (e: Exception)
        {
            Log.w("Test", "fail", e)
        }

        btnPetMoreInfoBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}