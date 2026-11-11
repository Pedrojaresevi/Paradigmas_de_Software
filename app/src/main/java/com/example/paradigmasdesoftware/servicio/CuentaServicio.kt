package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.repositorio.CuentaRepo

class CuentaServicio(
    private val cuentaRepo: CuentaRepo
) {
    suspend fun modificarDatos(
        emailActual: String,
        nuevoEmail: String,
        sesionActiva: Boolean,
        conexionBBDD: Boolean
    ): Boolean {
        // TODO ATDD: implementar validaciones de sesión, duplicados y llamadas al repo
        throw NotImplementedError("SUT sin implementar todavía")
    }
}