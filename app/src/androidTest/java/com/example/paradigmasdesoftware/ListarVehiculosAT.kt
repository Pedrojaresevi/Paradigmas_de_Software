package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.repositorio.FirebaseVehiculoRepo
import com.example.paradigmasdesoftware.servicio.VehiculoServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU14 (consulta de la lista de vehículos).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class ListarVehiculosAT {

    private lateinit var servicioVehiculo: VehiculoServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioVehiculo = VehiculoServicio(FirebaseVehiculoRepo())
    }

    // ---------- HU14-E01 (Válido: con vehículos registrados) ----------
    @Test
    fun HU14_E01() {
        runBlocking {
            // GIVEN: hay conexión a la BBDD y el usuario tiene vehículos registrados
            val conexionBBDD = true

            // WHEN: se solicita la lista de vehículos
            val lista = servicioVehiculo.listarVehiculos(conexionBBDD)

            // THEN: la lista se muestra con éxito (no nula)
            assertNotNull(lista)
        }
    }

    // ---------- HU14-E02 (Válido: sin vehículos registrados) ----------
    @Test
    fun HU14_E02() {
        runBlocking {
            // GIVEN: hay conexión a la BBDD pero el usuario NO tiene vehículos registrados
            val conexionBBDD = true

            // WHEN: se solicita la lista de vehículos
            val lista = servicioVehiculo.listarVehiculos(conexionBBDD)

            // THEN: la lista se muestra con éxito (vacía, pero mostrada)
            assertNotNull(lista)
            assertTrue(lista.isEmpty())
        }
    }

    // ---------- HU14-E03 (Inválido: sin conexión a la BBDD) ----------
    @Test(expected = NoConnectionException::class)
    fun HU14_E03() {
        runBlocking {
            // GIVEN: NO hay conexión a la BBDD
            val conexionBBDD = false

            // WHEN: se solicita la lista de vehículos
            // THEN: la lista NO se muestra → NoConnectionException
            servicioVehiculo.listarVehiculos(conexionBBDD)
        }
    }
}
