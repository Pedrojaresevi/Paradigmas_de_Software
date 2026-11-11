package com.example.paradigmasdesoftware.repositorio

/**
 * Implementación real contra Firebase Authentication.
 * ATDD: sin implementar todavía, lanza error para que las pruebas de aceptación fallen.
 */
class FirebaseAutRepo : AutenticacionRepo {

    override suspend fun cerrarSesion(sesionActiva: Boolean, conexionBBDD: Boolean, credencialActiva: Boolean): Boolean {
        // TODO ATDD: implementar con Firebase Authentication en mini-iteraciones
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
