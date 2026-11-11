package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InvalidVehicleDataException
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.modelo.Vehiculo
import com.example.paradigmasdesoftware.repositorio.FirebaseVehiculoRepo
import com.example.paradigmasdesoftware.servicio.VehiculoServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU16 (modificación de los datos de un vehículo).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class ModificarVehiculoAT {

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

    // ---------- HU16-E01 (Válido: actualización exitosa) ----------
    @Test
    fun HU16_E01() {
        runBlocking {
            // GIVEN: hay conexión a la BBDD y los nuevos datos del vehículo son válidos
            val datosValidos = true
            val conexionBBDD = true

            // WHEN: se solicita modificar el vehículo
            val resultado = servicioVehiculo.modificarVehiculo(
                vehiculoDePrueba(), datosValidos, conexionBBDD
            )

            // THEN: el vehículo se actualiza correctamente
            assertTrue(resultado)
        }
    }

    // ---------- HU16-E02 (Inválido: nuevos datos no válidos) ----------
    @Test(expected = InvalidVehicleDataException::class)
    fun HU16_E02() {
        runBlocking {
            // GIVEN: hay conexión a la BBDD pero los NUEVOS datos NO son válidos
            val datosValidos = false
            val conexionBBDD = true

            // WHEN: se solicita modificar el vehículo
            // THEN: el vehículo NO se actualiza → InvalidVehicleDataException
            servicioVehiculo.modificarVehiculo(vehiculoDePrueba(), datosValidos, conexionBBDD)
        }
    }

    // ---------- HU16-E03 (Inválido: sin conexión a la BBDD) ----------
    @Test(expected = NoConnectionException::class)
    fun HU16_E03() {
        runBlocking {
            // GIVEN: NO hay conexión a la BBDD
            val datosValidos = true
            val conexionBBDD = false

            // WHEN: se solicita modificar el vehículo
            // THEN: el vehículo NO se actualiza → NoConnectionException
            servicioVehiculo.modificarVehiculo(vehiculoDePrueba(), datosValidos, conexionBBDD)
        }
    }
}
