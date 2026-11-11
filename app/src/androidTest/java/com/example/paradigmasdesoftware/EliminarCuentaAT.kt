package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.OperationCancelledException
import com.example.paradigmasdesoftware.excepciones.SessionNotActiveException
import com.example.paradigmasdesoftware.repositorio.FirebaseCuentaRepo
import com.example.paradigmasdesoftware.servicio.CuentaServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU04 (eliminación de cuenta de usuario).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 *
 */
@RunWith(AndroidJUnit4::class)
class EliminarCuentaAT {

    private lateinit var servicioCuenta: CuentaServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioCuenta = CuentaServicio(FirebaseCuentaRepo())
    }

    // ---------- HU04-E01 (Válido: Eliminación confirmada) ----------
    @Test
    fun HU04_E01() {
        runBlocking {
            // GIVEN: El usuario está registrado en la BBDD, tiene la sesión activa
            // y acepta la confirmación del aviso de eliminación.
            val email = "rayanD@gmail.com"
            val sesionActiva = true
            val confirmacion = true

            // WHEN: se trata de borrar la cuenta
            val resultado = servicioCuenta.eliminarCuenta(email, confirmacion, sesionActiva)

            // THEN: el sistema elimina la cuenta y devuelve verdadero (éxito)
            assertTrue(resultado)
        }
    }

    // ---------- HU04-E02 (Inválido: Confirmación rechazada) ----------
    @Test(expected = OperationCancelledException::class)
    fun HU04_E02() {
        runBlocking {
            // GIVEN: El usuario está registrado y con sesión activa, pero
            // rechaza o no acepta la confirmación de borrado.
            val email = "rayanD@gmail.com"
            val sesionActiva = true
            val confirmacion = false

            // WHEN: se intenta eliminar la cuenta
            // THEN: Lanza OperationCancelledException
            servicioCuenta.eliminarCuenta(email, confirmacion, sesionActiva)
        }
    }

    // ---------- HU04-E03 (Inválido: Sin sesión activa) ----------
    @Test(expected = SessionNotActiveException::class)
    fun HU04_E03() {
        runBlocking {
            // GIVEN: El usuario consta en la BBDD pero intenta realizar la
            // eliminación sin disponer de una sesión activa.
            val email = "rayanD@gmail.com"
            val sesionActiva = false
            val confirmacion = true

            // WHEN: se intenta eliminar la cuenta
            // THEN: Lanza SessionNotActiveException (o similar para proteger los datos)
            servicioCuenta.eliminarCuenta(email, confirmacion, sesionActiva)
        }
    }
}