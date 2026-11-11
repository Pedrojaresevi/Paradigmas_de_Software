package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.repositorio.AutenticacionRepo

/**
 * SUT (System Under Test) de la HU03: cierre de sesión del usuario.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class AutenticacionServicio(
    private val autenticacionRepo: AutenticacionRepo
) {
    suspend fun cerrarSesion(sesionActiva: Boolean, conexionBBDD: Boolean, credencialActiva: Boolean): Boolean {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   HU03-E04 → SessionNotActiveException
        //   HU03-E03 → NoConnectionException
        //   HU03-E02 → InactiveCredentialException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
