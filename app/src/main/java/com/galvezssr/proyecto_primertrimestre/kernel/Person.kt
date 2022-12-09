package com.galvezssr.proyecto_primertrimestre.kernel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*Esto de aqui, es el constructor de la clase */
@Parcelize
data class Person(
    val nombre: String,
    val cumple: String,
    val apodo: String,
    val estado: String,
    val imagenUrl: String) : Parcelable
