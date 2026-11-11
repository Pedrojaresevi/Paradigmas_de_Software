package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InactiveCredentialException
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.excepciones.SessionNotActiveException
import com.example.paradigmasdesoftware.repositorio.FirebaseAutRepo
import com.example.paradigmasdesoftware.servicio.AutenticacionServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU03 (cierre de sesión del usuario).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class CerrarSesionAT {

    private lateinit var servicioAutenticacion: AutenticacionServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioAutenticacion = AutenticacionServicio(FirebaseAutRepo())
    }

    // ---------- HU03-E01 (Válido: cierre de sesión exitoso) ----------
    @Test
    fun HU03_E01() {
        runBlocking {
            // GIVEN: hay sesión activa, conexión a la BBDD y la credencial está activa
            val sesionActiva = true
            val conexionBBDD = true
            val credencialActiva = true

            // WHEN: se solicita cerrar la sesión
            val resultado = servicioAutenticacion.cerrarSesion(sesionActiva, conexionBBDD, credencialActiva)

            // THEN: la sesión se cierra correctamente
            assertTrue(resultado)
        }
    }

    // ---------- HU03-E02 (Inválido: credencial no activa) ----------
    @Test(expected = InactiveCredentialException::class)
    fun HU03_E02() {
        runBlocking {
            // GIVEN: hay sesión activa y conexión, pero la credencial NO está activa
            val sesionActiva = true
            val conexionBBDD = true
            val credencialActiva = false

            // WHEN: se solicita cerrar la sesión
            // THEN: la sesión NO se cierra → InactiveCredentialException
            servicioAutenticacion.cerrarSesion(sesionActiva, conexionBBDD, credencialActiva)
        }
    }

    // ---------- HU03-E03 (Inválido: sin conexión a la BBDD) ----------
    @Test(expected = NoConnectionException::class)
    fun HU03_E03() {
        runBlocking {
            // GIVEN: hay sesión activa pero NO hay conexión a la BBDD
            val sesionActiva = true
            val conexionBBDD = false
            val credencialActiva = true

            // WHEN: se solicita cerrar la sesión
            // THEN: la sesión NO se cierra → NoConnectionException
            servicioAutenticacion.cerrarSesion(sesionActiva, conexionBBDD, credencialActiva)
        }
    }

    // ---------- HU03-E04 (Inválido: sin sesión activa) ----------
    @Test(expected = SessionNotActiveException::class)
    fun HU03_E04() {
        runBlocking {
            // GIVEN: NO hay sesión activa
            val sesionActiva = false
            val conexionBBDD = true
            val credencialActiva = true

            // WHEN: se solicita cerrar la sesión
            // THEN: la sesión NO se cierra → SessionNotActiveException
            servicioAutenticacion.cerrarSesion(sesionActiva, conexionBBDD, credencialActiva)
        }
    }
}
