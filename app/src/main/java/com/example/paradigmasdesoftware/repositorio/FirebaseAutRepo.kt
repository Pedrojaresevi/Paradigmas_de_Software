package com.example.paradigmasdesoftware.repositorio

import com.example.paradigmasdesoftware.modelo.Usuario

/**
 * Implementación real contra Firebase Authentication.
 * ATDD: sin implementar todavía, lanza error para que las pruebas de aceptación fallen.
 */
class FirebaseAutRepo : AutenticacionRepo {

    override suspend fun registrar(email: String, contraseña: String): Usuario {
        // TODO ATDD: implementar con Firebase Authentication en mini-iteraciones
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
