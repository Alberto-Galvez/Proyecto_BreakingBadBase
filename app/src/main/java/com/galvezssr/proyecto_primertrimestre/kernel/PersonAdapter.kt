package com.galvezssr.proyecto_primertrimestre.kernel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galvezssr.proyecto_primertrimestre.R
import com.galvezssr.proyecto_primertrimestre.databinding.CardItemBinding

class PersonAdapter(val listener: (Person) -> Unit): RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    var personas = emptyList<Person>()

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.bind(personas[i])

        val persona = personas[i]
        holder.itemView.setOnClickListener {
            listener(persona)
        }
    }

    override fun getItemCount(): Int = personas.size

    ////////////////////////////////////////////////////
    // MINI-CLASES /////////////////////////////////////
    ////////////////////////////////////////////////////

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardItemBinding.bind(view)

        fun bind(persona: Person){

            binding.nombre.text = persona.nombre
            binding.apodo.text = persona.apodo

            Glide.with(binding.imagen).load(persona.imagenUrl).into(binding.imagen)

        }
    }
}

