package com.galvezssr.proyecto_primertrimestre.ui.main

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.galvezssr.proyecto_primertrimestre.kernel.Person
import com.galvezssr.proyecto_primertrimestre.kernel.server.RemoteConnection
import com.galvezssr.proyecto_primertrimestre.kernel.server.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

@SuppressLint("StaticFieldLeak")
class MainViewModel(private val app: AppCompatActivity): ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var resultado: RemoteResult
    private val _estado = MutableLiveData(UiState())
    val estado: LiveData<UiState> get() = _estado

    private var lista = emptyList<Person>()

    ////////////////////////////////////////////////////
    // FUNCIONES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _estado.value = estado.value?.copy(cargando = true)

            /** Se crea la lista con todos los personajes, en el que si el certificado SSL falla
             * o el tiempo de espera se agota, se guarden en la variable personajes estaticos **/
            try {
                showToast("Consultando Api...")

                resultado = RemoteConnection.servicio.obtenerTodosLosPersonajes()
                lista =  resultado.map {
                    Person(it.name, it.birthday, it.nickname, it.status, it.img)
                }

            } catch (e: Exception) {
                when (e) {

                    // Falla al acceder a la API
                    is SocketTimeoutException, is SSLException -> {
                        lista = listOf(
                            Person("Walter White", "09-07-1958", "Heisenberg", "Muerto", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg"),
                            Person("Walter White", "09-07-1958", "Heisenberg", "Muerto", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg"),
                            Person("Walter White", "09-07-1958", "Heisenberg", "Muerto", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg"),
                            Person("Jesse Pinkman", "09-24-1984", "Capitan Polla", "Vivo", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441"),
                            Person("Jesse Pinkman", "09-24-1984", "Capitan Polla", "Vivo", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441"),
                            Person("Jesse Pinkman", "09-24-1984", "Capitan Polla", "Vivo", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441")
                        )

                        showToast("Api no disponible, mostrando datos de prueba")
                    }

                    // Falla por no estalecer la conexion con internet
                    is UnknownHostException -> {
                        showToast("Conexion a internet no disponible")
                    }

                    else -> throw e
                }
            }

            _estado.value = estado.value?.copy(cargando = false, personajes = lista)
        }
    }

    fun navigateTo(personaje: Person) {
        _estado.value = _estado.value?.copy(navigateTo = personaje)
    }

    fun onNavigateDone(){
        _estado.value = _estado.value?.copy(navigateTo = null)
    }

    fun showToast(mensaje: String) {
        Toast.makeText(app.applicationContext, mensaje, Toast.LENGTH_LONG).show()
    }

    ////////////////////////////////////////////////////
    // MINI-CLASES /////////////////////////////////////
    ////////////////////////////////////////////////////

    data class UiState(
        val cargando: Boolean = false,
        val personajes: List<Person>? = null,
        val navigateTo: Person? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val app: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app) as T
    }
}