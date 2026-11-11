package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InvalidVehicleDataException
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
 * Pruebas de aceptación de la HU13 (alta de un vehículo).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class DarAltaVehiculoAT {

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

    // ---------- HU13-E01 (Válido: alta exitosa) ----------
    @Test
    fun HU13_E01() {
        runBlocking {
            // GIVEN: hay sesión activa, datos del vehículo válidos y conexión a la BBDD
            val sesionActiva = true
            val datosValidos = true
            val conexionBBDD = true

            // WHEN: se solicita dar de alta el vehículo
            val resultado = servicioVehiculo.darAltaVehiculo(
                vehiculoDePrueba(), sesionActiva, datosValidos, conexionBBDD
            )

            // THEN: el vehículo se da de alta correctamente
            assertTrue(resultado)
        }
    }

    // ---------- HU13-E02 (Inválido: sin conexión a la BBDD) ----------
    @Test(expected = NoConnectionException::class)
    fun HU13_E02() {
        runBlocking {
            // GIVEN: hay sesión activa y datos válidos pero NO hay conexión a la BBDD
            val sesionActiva = true
            val datosValidos = true
            val conexionBBDD = false

            // WHEN: se solicita dar de alta el vehículo
            // THEN: el vehículo NO se da de alta → NoConnectionException
            servicioVehiculo.darAltaVehiculo(
                vehiculoDePrueba(), sesionActiva, datosValidos, conexionBBDD
            )
        }
    }

    // ---------- HU13-E03 (Inválido: datos del vehículo no válidos) ----------
    @Test(expected = InvalidVehicleDataException::class)
    fun HU13_E03() {
        runBlocking {
            // GIVEN: hay sesión activa pero los datos del vehículo NO son válidos
            val sesionActiva = true
            val datosValidos = false
            val conexionBBDD = true

            // WHEN: se solicita dar de alta el vehículo
            // THEN: el vehículo NO se da de alta → InvalidVehicleDataException
            servicioVehiculo.darAltaVehiculo(
                vehiculoDePrueba(), sesionActiva, datosValidos, conexionBBDD
            )
        }
    }

    // ---------- HU13-E04 (Inválido: sin sesión activa) ----------
    @Test(expected = SessionNotActiveException::class)
    fun HU13_E04() {
        runBlocking {
            // GIVEN: NO hay sesión activa
            val sesionActiva = false
            val datosValidos = true
            val conexionBBDD = true

            // WHEN: se solicita dar de alta el vehículo
            // THEN: el vehículo NO se da de alta → SessionNotActiveException
            servicioVehiculo.darAltaVehiculo(
                vehiculoDePrueba(), sesionActiva, datosValidos, conexionBBDD
            )
        }
    }
}
