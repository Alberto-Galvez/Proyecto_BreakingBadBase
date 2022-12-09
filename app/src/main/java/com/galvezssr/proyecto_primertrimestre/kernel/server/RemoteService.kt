package com.galvezssr.proyecto_primertrimestre.kernel.server

import retrofit2.http.GET

interface RemoteService {
    @GET("characters")
    suspend fun obtenerTodosLosPersonajes(): RemoteResult
}