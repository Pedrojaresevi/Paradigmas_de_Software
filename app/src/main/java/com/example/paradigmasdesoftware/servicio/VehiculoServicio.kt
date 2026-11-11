package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.modelo.Vehiculo
import com.example.paradigmasdesoftware.repositorio.VehiculoRepo

/**
 * SUT (System Under Test) de la HU16: modificación de datos de un vehículo.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class VehiculoServicio(
    private val vehiculoRepo: VehiculoRepo
) {
    suspend fun modificarVehiculo(
        vehiculo: Vehiculo,
        datosValidos: Boolean,
        conexionBBDD: Boolean
    ): Boolean {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   HU16-E02 → InvalidVehicleDataException
        //   HU16-E03 → NoConnectionException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
