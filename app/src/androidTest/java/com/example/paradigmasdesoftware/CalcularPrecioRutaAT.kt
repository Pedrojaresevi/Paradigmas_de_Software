package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InvalidFuelTypeException
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.excepciones.RouteNotAvailableException
import com.example.paradigmasdesoftware.repositorio.FirebaseRutaRepo
import com.example.paradigmasdesoftware.servicio.RutaServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU17 (cálculo del precio de la ruta
 * según el tipo de combustible empleado).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class CalcularPrecioRutaAT {

    private lateinit var servicioRuta: RutaServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioRuta = RutaServicio(FirebaseRutaRepo())
    }

    // ---------- HU17-E01 (Válido: cálculo de precio exitoso) ----------
    @Test
    fun HU17_E01() {
        runBlocking {
            // GIVEN: hay conexión, tipo de combustible válido y ruta/distancia disponibles
            val tipoCombustible = "gasolina"
            val combustibleValido = true
            val distanciaDisponible = true
            val conexionBBDD = true

            // WHEN: se solicita el cálculo del precio de la ruta
            val precio = servicioRuta.calcularPrecioRuta(
                tipoCombustible, combustibleValido, distanciaDisponible, conexionBBDD
            )

            // THEN: el precio se calcula con éxito
            assertTrue(precio >= 0.0)
        }
    }

    // ---------- HU17-E02 (Inválido: tipo de combustible no válido) ----------
    @Test(expected = InvalidFuelTypeException::class)
    fun HU17_E02() {
        runBlocking {
            // GIVEN: hay conexión pero el tipo de combustible NO es válido
            val tipoCombustible = "combustible_inexistente"
            val combustibleValido = false
            val distanciaDisponible = true
            val conexionBBDD = true

            // WHEN: se solicita el cálculo del precio de la ruta
            // THEN: NO se calcula → InvalidFuelTypeException
            servicioRuta.calcularPrecioRuta(
                tipoCombustible, combustibleValido, distanciaDisponible, conexionBBDD
            )
        }
    }

    // ---------- HU17-E03 (Inválido: ruta/distancia no disponible) ----------
    @Test(expected = RouteNotAvailableException::class)
    fun HU17_E03() {
        runBlocking {
            // GIVEN: hay conexión y combustible válido pero la ruta/distancia NO está disponible
            val tipoCombustible = "gasolina"
            val combustibleValido = true
            val distanciaDisponible = false
            val conexionBBDD = true

            // WHEN: se solicita el cálculo del precio de la ruta
            // THEN: NO se calcula → RouteNotAvailableException
            servicioRuta.calcularPrecioRuta(
                tipoCombustible, combustibleValido, distanciaDisponible, conexionBBDD
            )
        }
    }

    // ---------- HU17-E04 (Inválido: sin conexión a la BBDD/API) ----------
    @Test(expected = NoConnectionException::class)
    fun HU17_E04() {
        runBlocking {
            // GIVEN: NO hay conexión a la BBDD/API
            val tipoCombustible = "gasolina"
            val combustibleValido = true
            val distanciaDisponible = true
            val conexionBBDD = false

            // WHEN: se solicita el cálculo del precio de la ruta
            // THEN: NO se calcula → NoConnectionException
            servicioRuta.calcularPrecioRuta(
                tipoCombustible, combustibleValido, distanciaDisponible, conexionBBDD
            )
        }
    }
}
