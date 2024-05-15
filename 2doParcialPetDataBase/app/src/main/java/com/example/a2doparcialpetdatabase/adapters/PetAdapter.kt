package com.example.a2doparcialpetdatabase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.entities.Pets

class PetAdapter (
    var petList : MutableList<Pets>,
    var onClick: (Int)->Unit)
    : RecyclerView.Adapter<PetAdapter.PetsHolder>(){

        class PetsHolder (v:View) : RecyclerView.ViewHolder(v){
            private var view: View
            init{
                this.view = v
            }
            fun setName (name :String){
                var txtName : TextView = view.findViewById(R.id.txtItemPetName)
                txtName.text = name
            }

            fun setImage (imageurl : String){
                var image: ImageView = view.findViewById(R.id.imgGuitar)
                Glide.with(view).load(imageurl).into(image)
            }
            // Para escuchar el click del card
            fun getCard() : CardView {
                return view.findViewById(R.id.cardGuitar)
            }

        }

    // Devuelve el componenteHolder - Busca el item y devuelve una instancia del holter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet,parent, false)
        return (PetsHolder(view))
    }

    // Devuelve la cantidad de elementos de la lista
    override fun getItemCount(): Int {
        return petList.size
    }

    // Une la informacion con el viewholder
    override fun onBindViewHolder(holder: PetsHolder, position: Int) {
        holder.setName(petList[position].name)
        //holder.setId(guitarList[position].id)
        holder.setImage(petList[position].imageUrl)
        holder.getCard().setOnClickListener{
            // Lo que quiera hacer al cliclear, el resto se ejecuta en el fragment correspondiente
            onClick(position)
        }
    }

}