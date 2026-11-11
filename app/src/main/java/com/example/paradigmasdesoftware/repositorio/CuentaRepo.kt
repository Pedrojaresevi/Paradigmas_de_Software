package com.example.paradigmasdesoftware.repositorio

interface CuentaRepo {
    suspend fun modificarDatos(emailActual: String, nuevoEmail: String): Boolean
}