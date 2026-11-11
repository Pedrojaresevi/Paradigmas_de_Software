package com.example.paradigmasdesoftware.repositorio

interface CuentaRepo {
    suspend fun eliminarCuenta(email: String): Boolean
}