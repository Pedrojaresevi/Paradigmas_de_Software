package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.InvalidEmailException
import com.example.paradigmasdesoftware.excepciones.NoConnectionException
import com.example.paradigmasdesoftware.excepciones.UserAlreadyExistsException
import com.example.paradigmasdesoftware.excepciones.WeakPasswordException
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
 * Pruebas de aceptación de la HU01 (registro de usuarios).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class AutenticacionUsuarioAT {

    private lateinit var servicioAutenticacion: AutenticacionServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioAutenticacion = AutenticacionServicio(FirebaseAutRepo())
    }

    // ---------- HU01-E01 (Válido) ----------
    @Test
    fun HU01_E01() {
        runBlocking {
            // GIVEN: hay conexión, usuario con email válido y contraseña válida
            val email = "pedro@example.com"
            val contraseña = "12345678"

            // WHEN: se intenta registrarse
            val usuario = servicioAutenticacion.registrar(email, contraseña)

            // THEN: el registro es válido y se devuelve el usuario
            assertNotNull(usuario)
            assertEquals(email, usuario.email)
        }
    }

    // ---------- HU01-E02 (Inválido: contraseña no válida) ----------
    @Test(expected = WeakPasswordException::class)
    fun HU01_E02() {
        runBlocking {
            // GIVEN: hay conexión, usuario válido pero contraseña de 3 caracteres
            // WHEN: se intenta registrarse
            // THEN: el registro NO es válido → WeakPasswordException
            servicioAutenticacion.registrar("pedro@example.com", "abc")
        }
    }

    // ---------- HU01-E03 (Inválido: usuario no válido) ----------
    @Test
    fun HU01_E03() {
        // Caso a: email con formato inválido
        // GIVEN: hay conexión y un email sin formato válido
        // WHEN: se intenta registrarse
        // THEN: el registro NO es válido → InvalidEmailException
        assertThrows(InvalidEmailException::class.java) {
            runBlocking {
                servicioAutenticacion.registrar("esto-no-es-un-email", "12345678")
            }
        }

        // Caso b: usuario ya registrado
        // GIVEN: hay conexión y el usuario ya está registrado
        runBlocking {
            servicioAutenticacion.registrar("pedro@example.com", "12345678")
        }
        // WHEN: se intenta registrarse otra vez con el mismo email
        // THEN: el registro NO es válido → UserAlreadyExistsException
        assertThrows(UserAlreadyExistsException::class.java) {
            runBlocking {
                servicioAutenticacion.registrar("pedro@example.com", "12345678")
            }
        }
    }

    // ---------- HU01-E04 (Inválido: sin conexión) ----------
    @Test(expected = NoConnectionException::class)
    fun HU01_E04() {
        runBlocking {
            // GIVEN: NO hay conexión a la BBDD
            //   (en la fase de implementación se apuntará a un host muerto,
            //    p.ej. firebaseAuth.useAuthEmulator("10.0.2.1", 9999))
            // WHEN: se intenta registrarse
            // THEN: el registro NO es válido → NoConnectionException
            servicioAutenticacion.registrar("pedro@example.com", "12345678")
        }
    }
}
