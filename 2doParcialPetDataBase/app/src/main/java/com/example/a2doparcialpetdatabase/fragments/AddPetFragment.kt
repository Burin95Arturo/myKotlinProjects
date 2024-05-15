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
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.viewmodels.AddPetViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPetFragment : Fragment() {

    companion object {
        fun newInstance() = AddPetFragment()
    }

    private lateinit var viewModel: AddPetViewModel

    lateinit var v : View
    lateinit var edtAddPetOwnerEmail : EditText
    lateinit var edtAddPetOwner : EditText
    lateinit var edtAddPetName : EditText
    lateinit var edtAddPetNotes : EditText
    lateinit var edtAddPetBreed : EditText
    lateinit var btnAddPetAdd : Button
    lateinit var btnAddPetBack : Button
    lateinit var btnAddPetImage : ImageButton
    lateinit var edtAddPetAge : EditText

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_add_pet, container, false)

        edtAddPetOwnerEmail = v.findViewById(R.id.edtAddPetOwnerEmail)
        edtAddPetOwner = v.findViewById(R.id.edtAddPetOwner)
        edtAddPetName = v.findViewById(R.id.edtAddPetName)
        edtAddPetNotes = v.findViewById(R.id.edtAddPetNotes)
        edtAddPetBreed = v.findViewById(R.id.edtAddPetBreed)
        edtAddPetAge = v.findViewById(R.id.edtAddPetAge)
        btnAddPetAdd = v.findViewById(R.id.btnAddPetAdd)
        btnAddPetBack = v.findViewById(R.id.btnAddPetBack)
        btnAddPetImage = v.findViewById(R.id.btnAddPetImage)

        // Initialize Firebase Auth
        auth = Firebase.auth

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddPetViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        /*db.collection("pets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Test", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener{

            }*/

        var imgurl = "https://i.pinimg.com/236x/58/4e/8f/584e8fa980a616384accc138ba8c107d.jpg"
        btnAddPetAdd.setOnClickListener{

            if(edtAddPetName.length()>0 && edtAddPetOwner.length()>0){

                val newItem = db.collection("pets").document()
                val newUid = newItem.id

                viewModel.setPet(newUid,
                        edtAddPetName.text.toString(),
                        edtAddPetBreed.text.toString(),
                        imgurl,
                        edtAddPetNotes.text.toString(),
                        edtAddPetAge.text.toString().toInt(),
                        edtAddPetOwner.text.toString(),
                        edtAddPetOwnerEmail.text.toString()
                )

                //viewModel.showLastPet()
                //val newItem = db.collection("pets").document()
                //val newUid = newItem.id

                db.collection("pets").document(newUid).set(viewModel.pets)
                   /* .add(viewModel.pets)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Test", "Pet added : ${viewModel.getPet().name}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Test", "Error adding document", e)
                    }*/
                findNavController().navigateUp()

            }else{
                Snackbar.make(v, "Nothing to add", Snackbar.LENGTH_SHORT).show()
            }
        }

        btnAddPetBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}