package com.example.paradigmasdesoftware.repositorio

class FirebaseCuentaRepo : CuentaRepo {
    override suspend fun eliminarCuenta(email: String): Boolean {
        // TODO ATDD: implementar con Firebase
        throw NotImplementedError("SUT sin implementar todavía")
    }
}