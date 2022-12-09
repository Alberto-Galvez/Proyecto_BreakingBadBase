package com.galvezssr.proyecto_primertrimestre.kernel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.galvezssr.proyecto_primertrimestre.R

// Indicamos que este activity exiende del XML: activity_host
class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        /** Esto inicia el XML: activity_main, que dentro lo que hace es ir al xml de navegacion
         * y arrancar el MainFragment, por lo que nos vamos a este **/

    }
}

