package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.modelo.Vehiculo
import com.example.paradigmasdesoftware.repositorio.VehiculoRepo

/**
 * SUT (System Under Test) de la HU14: consulta de la lista de vehículos.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class VehiculoServicio(
    private val vehiculoRepo: VehiculoRepo
) {
    suspend fun listarVehiculos(conexionBBDD: Boolean): List<Vehiculo> {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   HU14-E03 → NoConnectionException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
