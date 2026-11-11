package com.example.paradigmasdesoftware.repositorio

/**
 * Implementación real contra Firebase (Firestore).
 * ATDD: sin implementar todavía, lanza error para que las pruebas de aceptación fallen.
 */
class FirebaseRutaRepo : RutaRepo {
    override suspend fun calcularPrecioRuta(tipoCombustible: String): Double {
        // TODO ATDD: implementar con Firebase en mini-iteraciones
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
