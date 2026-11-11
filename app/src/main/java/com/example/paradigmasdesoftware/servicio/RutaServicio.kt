package com.example.paradigmasdesoftware.servicio

import com.example.paradigmasdesoftware.repositorio.RutaRepo

/**
 * SUT (System Under Test) de la HU17: cálculo del precio de la ruta
 * según el tipo de combustible empleado.
 * Contiene la lógica de negocio (validaciones); el repositorio solo persiste.
 *
 * ATDD: implementación mínima SIN lógica para que las pruebas de aceptación fallen.
 */
class RutaServicio(
    private val rutaRepo: RutaRepo
) {
    suspend fun calcularPrecioRuta(
        tipoCombustible: String,
        combustibleValido: Boolean,
        distanciaDisponible: Boolean,
        conexionBBDD: Boolean
    ): Double {
        // TODO ATDD: implementar en mini-iteraciones durante la iteración 1
        //   HU17-E02 → InvalidFuelTypeException
        //   HU17-E03 → RouteNotAvailableException
        //   HU17-E04 → NoConnectionException
        throw NotImplementedError("SUT sin implementar todavía")
    }
}
