package com.example.paradigmasdesoftware.repositorio

class FirebaseCuentaRepo : CuentaRepo {
    override suspend fun modificarDatos(emailActual: String, nuevoEmail: String): Boolean {
        throw NotImplementedError("SUT sin implementar todavía")
    }
}