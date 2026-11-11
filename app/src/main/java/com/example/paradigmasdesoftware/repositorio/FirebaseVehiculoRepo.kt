package com.example.paradigmasdesoftware.repositorio

import com.example.paradigmasdesoftware.modelo.Vehiculo

/**
 * Implementación real contra Firebase (Firestore).
 * ATDD: sin implementar todavía, lanza error para que las pruebas de aceptación fallen.
 */
class FirebaseVehiculoRepo : VehiculoRepo {
    override suspend fun modificarVehiculo(vehiculo: Vehiculo): Boolean {
        // TODO ATDD: implementar con Firebase en mini-iteraciones
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
