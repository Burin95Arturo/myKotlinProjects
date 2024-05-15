package com.example.primerparcialapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.primerparcialapp.R
import com.example.primerparcialapp.entities.Guitarra
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class GuitarraAdapter (
    var guitarList : MutableList <Guitarra>,
    var onClick : (Int) -> Unit) // Detecta el click en los items del recyclerview
    : RecyclerView.Adapter<GuitarraAdapter.GuitarHolder>(){

    class GuitarHolder (v: View) : RecyclerView.ViewHolder(v){
        private var view: View
        init {
            this.view = v
        }
        fun setName (name :String){
        var txtName : TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }
        fun setId (id : Int){
        var txtId : TextView = view.findViewById(R.id.txtId)
            txtId.text = id.toString()
        }
        fun setImage (imageurl : String){
            var image: ImageView = view.findViewById(R.id.imgGuitar)
            Glide.with(view).load(imageurl).into(image)
        }
        // Para escuchar el click del card
        fun getCard() : CardView{
            return view.findViewById(R.id.cardGuitar)
        }
    }

    // Devuelve el componenteHolder - Busca el item y devuelve una instancia del holter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuitarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guitar,parent, false)
        return (GuitarHolder(view))
    }

    // Devuelve la cantidad de elementos de la lista
    override fun getItemCount(): Int {
        return guitarList.size
    }

    // Une la informacion con el viewholder
    override fun onBindViewHolder(holder: GuitarHolder, position: Int) {
        holder.setName(guitarList[position].name)
        holder.setId(guitarList[position].id)
        holder.setImage(guitarList[position].imageUrl)
        holder.getCard().setOnClickListener{
            // Lo que quiera hacer al cliclear, el resto se ejecuta en el fragment correspondiente
            onClick(position)
        }
    }
}