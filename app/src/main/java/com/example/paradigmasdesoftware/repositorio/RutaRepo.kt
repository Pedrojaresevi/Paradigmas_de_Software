package com.example.paradigmasdesoftware.repositorio

/**
 * Contrato que define qué operaciones ofrece el repositorio de rutas.
 * Permite intercambiar la implementación real (Firebase) por un mock en los tests.
 */
interface RutaRepo {
    suspend fun calcularPrecioRuta(tipoCombustible: String): Double
}
