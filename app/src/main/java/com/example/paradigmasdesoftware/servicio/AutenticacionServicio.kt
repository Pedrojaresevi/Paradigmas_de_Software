package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.modelo.Usuario
import com.example.paradigmasdesoftware.repositorio.AutenticacionRepo

/**
 * SUT (System Under Test) de la HU01: registro de usuarios.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class AutenticacionServicio(
    private val autenticacionRepo: AutenticacionRepo
) {
    suspend fun registrar(email: String, contraseña: String): Usuario {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   E03 → InvalidEmailException / UserAlreadyExistsException
        //   E02 → WeakPasswordException
        //   E04 → NoConnectionException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
