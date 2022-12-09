package com.galvezssr.proyecto_primertrimestre.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.galvezssr.proyecto_primertrimestre.R
import com.galvezssr.proyecto_primertrimestre.databinding.MainFragmentBinding
import com.galvezssr.proyecto_primertrimestre.kernel.PersonAdapter
import com.galvezssr.proyecto_primertrimestre.ui.detail.DetailFragment

// Indicamos que este activity exiende del XML: main_fragment
class MainFragment: Fragment(R.layout.main_fragment) {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    // Recuperar: ViewModel
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory((requireActivity() as AppCompatActivity)) }
    private val adapterPersonas = PersonAdapter { person -> viewModel.navigateTo(person) }
    private lateinit var binding: MainFragmentBinding

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    // Inflar: MainFragment
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Establecer: Recycler
        binding = MainFragmentBinding.bind(view).apply{
            recycler.adapter = adapterPersonas
        }

        // Establecer: Barra de navegacion
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Personajes de Breaking Bad"

        /** Establecido en el Manifest que no queremos barra de navegacion, esta se
         * construye manualmente en el XML, diciendole aqui que el titulo sea ese **/

        // Establecer: ViewModel
        viewModel.estado.observe(viewLifecycleOwner) {
            estado -> binding.indicadorProgreso.visibility = if (estado.cargando) VISIBLE else GONE

            estado.personajes?.let {
                adapterPersonas.personas = estado.personajes
                adapterPersonas.notifyDataSetChanged()
            }

            estado.navigateTo?.let {

                /** Navegamos hacia el DetailFragment pasandole un personaje **/
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragment, bundleOf(DetailFragment.SELECTED_PERSON to it)
                )

                viewModel.onNavigateDone()
            }
        }
    }
}