package com.example.paradigmasdesoftware.repositorio

/**
 * Contrato que define qué operaciones de autenticación ofrece el repositorio.
 * Permite intercambiar la implementación real (Firebase) por un mock en los tests.
 */
interface AutenticacionRepo {
    suspend fun cerrarSesion(sesionActiva: Boolean, conexionBBDD: Boolean, credencialActiva: Boolean): Boolean
}
