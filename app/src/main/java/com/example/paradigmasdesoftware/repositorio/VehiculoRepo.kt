package com.example.paradigmasdesoftware.repositorio

import com.example.paradigmasdesoftware.modelo.Vehiculo

/**
 * Contrato que define qué operaciones ofrece el repositorio de vehículos.
 * Permite intercambiar la implementación real (Firebase) por un mock en los tests.
 */
interface VehiculoRepo {
    suspend fun darAltaVehiculo(vehiculo: Vehiculo): Boolean
    suspend fun listarVehiculos(): List<Vehiculo>
    suspend fun eliminarVehiculo(vehiculo: Vehiculo): Boolean
    suspend fun modificarVehiculo(vehiculo: Vehiculo): Boolean
}
