package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.modelo.Usuario
import com.example.paradigmasdesoftware.repositorio.AutenticacionRepo

/**
 * SUT (System Under Test) de la HU02: inicio de sesión de usuarios.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class AutenticacionServicio(
    private val autenticacionRepo: AutenticacionRepo
) {
    suspend fun iniciarSesion(email: String, contraseña: String): Usuario {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   HU02-E03 → InvalidEmailException
        //   HU02-E04 → UserNotFoundException
        //   HU02-E02 → WrongPasswordException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
