package com.example.primerparcialapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.primerparcialapp.R
import com.example.primerparcialapp.adapters.GuitarraAdapter
import com.example.primerparcialapp.database.appDatabase
import com.example.primerparcialapp.database.guitarDao

import com.example.primerparcialapp.database.userDao
import com.example.primerparcialapp.entities.Guitarra

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppFragment : Fragment() {

    lateinit var v : View

    lateinit var recGuitar : RecyclerView
    lateinit var adapter : GuitarraAdapter
    var guitarList : MutableList<Guitarra> = mutableListOf()
    private var guitarDao : guitarDao? = null
    private var db: appDatabase? = null
    lateinit var btnAdd: FloatingActionButton

    var cont : Int = 0

    val imgstring1 : String = "https://www.fmicassets.com/Damroot/JacksonVert/0/2910124568_gtr_frt_001_rr.png"
    val imgstring2 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10004/2914327580_jac_ins_frt_1_rr.png"
    val imgstring3 : String = "https://www.fmicassets.com/Damroot/JacksonVert/0/2910124572_gtr_frt_001_rr.png"
    val imgstring4 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10004/2911101566_jac_ins_frt_1_rr.png"
    val imgstring5 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10001/2910135539_gtr_frt_001_rr.png"
    val imgstring6 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10004/2919914518_jac_ins_frt_01_rr.png"
    val imgstring7 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10002/2916403587_jac_ins_frt_1_rr.png"
    val imgstring8 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10002/2916787503_jac_ins_frt_1_rr.png"
    val imgstring9 : String = "https://www.fmicassets.com/Damroot/JacksonVert/10003/2916600568_jac_ins_frt_01_rr.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_app, container, false)

        recGuitar = v.findViewById(R.id.recGuitar)
        btnAdd = v.findViewById((R.id.btnActionAdd))

        return v
    }
    override fun onStart() {
        super.onStart()
        Log.d("TEST", "Ingreso a fragment App\n")
        db = appDatabase.getAppDataBase(v.context)
        guitarDao = db?.guitarDao()

        guitarList = guitarDao?.loadAllGuitar() as MutableList<Guitarra>

        adapter = GuitarraAdapter(guitarList) { position ->
            /*Snackbar.make(v, "Click en ${guitarraRepo.guitars[position].name}", Snackbar.LENGTH_SHORT).show()*/
            val action = AppFragmentDirections.actionAppFragmentToAppDetailedFragment(guitarList[position].id)
            v.findNavController().navigate(action)
        }
        recGuitar.layoutManager = LinearLayoutManager(context)
        recGuitar.adapter = adapter

        if(cont == 0){
            Log.d("TEST", "no hay datos, los cargo\n")
            //Inserto elementos a la lista
            guitarDao?.insertGuitar(Guitarra(cont,"JS SERIES KELLY™ JS32T", "Jackson", imgstring1,
                "Electric Guitars", "Poplar", "Amaranth", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"PRO PLUS SERIES SOLOIST™ SLA3Q", "Jackson", imgstring2,
                "Electric Guitars", "Okoume", "Ebony", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"JS SERIES KING V™ JS32", "Jackson", imgstring3,
                "Electric Guitars", "Poplar", "Amaranth", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"PRO PLUS DINKY® MDK HT7 MS", "Jackson", imgstring4,
                "Electric Guitars", "Basswood", "Ebony", 7, "Japon"))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"JS SERIES KING V™ JS32T", "Jackson", imgstring5,
                "Electric Guitars", "Poplar", "Amaranth", 6, "Japon" ))
            cont+=1

            guitarDao?.insertGuitar(Guitarra(cont,"X SERIES SOLOIST™ SLX DX", "Jackson", imgstring6,
                "Electric Guitars", "Poplar", "Laurel", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"X SERIES SIGNATURE SCOTT IAN KING V™", "Jackson", imgstring7,
                "Electric Guitars", "Nyatoh", "Rosewood", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"PRO SERIES SIGNATURE MARK HEYLMUN RHOADS RR24-7, EBONY FINGERBOARD, LUX", "Jackson",
                imgstring8,"Electric Guitars", "Nyatoh", "Ebony", 6, "Japon" ))
            cont+=1
            guitarDao?.insertGuitar(Guitarra(cont,"X SERIES WARRIOR™ WRX24", "Jackson", imgstring9,
                "Electric Guitars", "Poplar", "Laurel", 6, "Japon" ))
        }

        btnAdd.setOnClickListener{
            //Snackbar.make(v, "Floating button", Snackbar.LENGTH_SHORT).show()
            val action = AppFragmentDirections.actionAppFragmentToAddGuitarFragment(cont)
            v.findNavController().navigate(action)
        }
    }
}