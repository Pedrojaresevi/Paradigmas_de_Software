package com.example.paradigmasdesoftware.repositorio

import com.example.paradigmasdesoftware.modelo.Usuario

/**
 * Contrato que define qué operaciones de autenticación ofrece el repositorio.
 * Permite intercambiar la implementación real (Firebase) por un mock en los tests.
 */
interface AutenticacionRepo {
    suspend fun registrar(email: String, contraseña: String): Usuario
}
