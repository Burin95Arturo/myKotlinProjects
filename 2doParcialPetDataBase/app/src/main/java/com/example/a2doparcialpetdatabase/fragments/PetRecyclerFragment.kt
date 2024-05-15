package com.example.a2doparcialpetdatabase.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.adapters.PetAdapter
import com.example.a2doparcialpetdatabase.entities.Pets
import com.example.a2doparcialpetdatabase.viewmodels.PetRecyclerViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PetRecyclerFragment : Fragment() {

    companion object {
        fun newInstance() = PetRecyclerFragment()
    }

    private lateinit var viewModel: PetRecyclerViewModel
    lateinit var v : View
    lateinit var recPet: RecyclerView
    lateinit var adapter: PetAdapter
    lateinit var btnPetRecyclerAdd : FloatingActionButton

    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pet_recycler, container, false)

        recPet = v.findViewById(R.id.recPet)
        btnPetRecyclerAdd = v.findViewById(R.id.btnPetRecyclerAdd)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PetRecyclerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        /*db.collection("pets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    viewModel.addObjectToList(document.toObject(Pets::class.java))
                    Log.d("Test", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener{

            }*/

        /*var petList = viewModel.getList()

        adapter = PetAdapter(petList) { position ->
           val action = PetRecyclerFragmentDirections.actionPetRecyclerFragmentToPetInfoFragment(petList[position].name)
           v.findNavController().navigate(action)
        }
        recPet.layoutManager = LinearLayoutManager(context)
        recPet.adapter = adapter*/

        btnPetRecyclerAdd.setOnClickListener{
            val action = PetRecyclerFragmentDirections.actionPetRecyclerFragmentToAddPetFragment()
            findNavController().navigate(action)
        }

        viewModel.getPets()
        // Observe changes in the documentList
        viewModel.documentList.observe(viewLifecycleOwner)
        {petList ->
            // Handle the updated documentList as needed
            // For example, update your UI with the new data
            adapter = PetAdapter(petList) { position ->
                val action = PetRecyclerFragmentDirections.actionPetRecyclerFragmentToPetInfoFragment(petList[position].uid)
                v.findNavController().navigate(action)
            }
            recPet.layoutManager = LinearLayoutManager(context)
            recPet.adapter = adapter
        }
    }
}