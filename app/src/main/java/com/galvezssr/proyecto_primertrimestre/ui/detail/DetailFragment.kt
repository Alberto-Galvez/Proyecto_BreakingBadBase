package com.galvezssr.proyecto_primertrimestre.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.galvezssr.proyecto_primertrimestre.R
import com.galvezssr.proyecto_primertrimestre.databinding.DetailFragmentBinding

// Indicamos que este activity exiende del XML: detail_fragment
class DetailFragment: Fragment(R.layout.detail_fragment) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    // Definir: Variable Estatica
    companion object { const val SELECTED_PERSON = "personaSeleccionada" }

    // Recuperar: ViewModel
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(arguments?.getParcelable(SELECTED_PERSON)!!) }

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    // Inflar: DetailFragment
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailFragmentBinding.bind(view)

        // Establecer: Barra de navegacion
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // Establecer: ViewModel
        viewModel.persona.observe(viewLifecycleOwner){

            /** Creamos una varible persona y cargamos los datos de la misma **/
            person -> (requireActivity() as AppCompatActivity).supportActionBar?.title = person.nombre

            Glide.with(binding.imagen).load(person.imagenUrl).into(binding.imagen)
            binding.apodo.text = "Apodo: " + person.apodo
            binding.nacimiento.text = "Fecha de nacimiento: " + person.cumple
            binding.estado.text = "Estado actual: " + person.estado
        }
    }
}