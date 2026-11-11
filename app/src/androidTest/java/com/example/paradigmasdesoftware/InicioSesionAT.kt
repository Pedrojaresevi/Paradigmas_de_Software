package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InvalidEmailException
import com.example.paradigmasdesoftware.excepciones.UserNotFoundException
import com.example.paradigmasdesoftware.excepciones.WrongPasswordException
import com.example.paradigmasdesoftware.repositorio.FirebaseAutRepo
import com.example.paradigmasdesoftware.servicio.AutenticacionServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU02 (inicio de sesión de usuarios).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class InicioSesionAT {

    private lateinit var servicioAutenticacion: AutenticacionServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioAutenticacion = AutenticacionServicio(FirebaseAutRepo())
    }

    // ---------- HU02-E01 (Válido) ----------
    @Test
    fun HU02_E01() {
        runBlocking {
            // GIVEN: hay conexión, el usuario está registrado, con email y contraseña válidos
            val email = "pedro@example.com"
            val contraseña = "12345678"

            // WHEN: se intenta iniciar sesión
            val usuario = servicioAutenticacion.iniciarSesion(email, contraseña)

            // THEN: la sesión se inicia y se devuelve el usuario
            assertNotNull(usuario)
            assertEquals(email, usuario.email)
        }
    }

    // ---------- HU02-E02 (Inválido: contraseña no válida) ----------
    @Test(expected = WrongPasswordException::class)
    fun HU02_E02() {
        runBlocking {
            // GIVEN: hay conexión, el usuario está registrado y el email es válido,
            //        pero la contraseña es incorrecta
            // WHEN: se intenta iniciar sesión
            // THEN: la sesión NO se inicia → WrongPasswordException
            servicioAutenticacion.iniciarSesion("pedro@example.com", "clave-incorrecta")
        }
    }

    // ---------- HU02-E03 (Inválido: email no válido) ----------
    @Test
    fun HU02_E03() {
        // GIVEN: hay conexión y un email sin formato válido
        // WHEN: se intenta iniciar sesión
        // THEN: la sesión NO se inicia → InvalidEmailException
        assertThrows(InvalidEmailException::class.java) {
            runBlocking {
                servicioAutenticacion.iniciarSesion("esto-no-es-un-email", "12345678")
            }
        }
    }

    // ---------- HU02-E04 (Inválido: no registrado en la BBDD) ----------
    @Test(expected = UserNotFoundException::class)
    fun HU02_E04() {
        runBlocking {
            // GIVEN: hay conexión pero el usuario NO está registrado en la BBDD
            // WHEN: se intenta iniciar sesión
            // THEN: la sesión NO se inicia → UserNotFoundException
            servicioAutenticacion.iniciarSesion("no-existe@example.com", "12345678")
        }
    }
}
