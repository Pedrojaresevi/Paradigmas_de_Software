package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.excepciones.SessionNotActiveException
import com.example.paradigmasdesoftware.modelo.Vehiculo
import com.example.paradigmasdesoftware.repositorio.FirebaseVehiculoRepo
import com.example.paradigmasdesoftware.servicio.VehiculoServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU15 (eliminación de un vehículo).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class EliminarVehiculoAT {

    private lateinit var servicioVehiculo: VehiculoServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioVehiculo = VehiculoServicio(FirebaseVehiculoRepo())
    }

    private fun vehiculoDePrueba() = Vehiculo(
        matricula = "1234ABC",
        marca = "Seat",
        modelo = "León"
    )

    // ---------- HU15-E01 (Válido: eliminación exitosa) ----------
    @Test
    fun HU15_E01() {
        runBlocking {
            // GIVEN: hay sesión activa y conexión a la BBDD
            val sesionActiva = true
            val conexionBBDD = true

            // WHEN: se solicita eliminar el vehículo
            val resultado = servicioVehiculo.eliminarVehiculo(
                vehiculoDePrueba(), sesionActiva, conexionBBDD
            )

            // THEN: el vehículo se elimina correctamente
            assertTrue(resultado)
        }
    }

    // ---------- HU15-E02 (Inválido: sin conexión a la BBDD) ----------
    @Test(expected = NoConnectionException::class)
    fun HU15_E02() {
        runBlocking {
            // GIVEN: hay sesión activa pero NO hay conexión a la BBDD
            val sesionActiva = true
            val conexionBBDD = false

            // WHEN: se solicita eliminar el vehículo
            // THEN: el vehículo NO se elimina → NoConnectionException
            servicioVehiculo.eliminarVehiculo(vehiculoDePrueba(), sesionActiva, conexionBBDD)
        }
    }

    // ---------- HU15-E03 (Inválido: sin sesión activa) ----------
    @Test(expected = SessionNotActiveException::class)
    fun HU15_E03() {
        runBlocking {
            // GIVEN: NO hay sesión activa
            val sesionActiva = false
            val conexionBBDD = true

            // WHEN: se solicita eliminar el vehículo
            // THEN: el vehículo NO se elimina → SessionNotActiveException
            servicioVehiculo.eliminarVehiculo(vehiculoDePrueba(), sesionActiva, conexionBBDD)
        }
    }
}
