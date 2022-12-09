package com.galvezssr.proyecto_primertrimestre.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.galvezssr.proyecto_primertrimestre.kernel.Person

class DetailViewModel(person: Person): ViewModel() {
    private val personaje = MutableLiveData(person)
    val persona: LiveData<Person> get() = personaje
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val person: Person): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(person) as T
    }
}