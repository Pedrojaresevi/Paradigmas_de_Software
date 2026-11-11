package com.example.paradigmasdesoftware

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.paradigmasdesoftware.excepciones.DatabaseConnectionException
import com.example.paradigmasdesoftware.excepciones.DuplicateEntityException
import com.example.paradigmasdesoftware.excepciones.InvalidFormatException
import com.example.paradigmasdesoftware.excepciones.SessionNotActiveException
import com.example.paradigmasdesoftware.repositorio.FirebaseCuentaRepo
import com.example.paradigmasdesoftware.servicio.CuentaServicio
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de aceptación de la HU05 (modificar los datos de la cuenta).
 * Usan la dependencia REAL (Firebase) → extremo a extremo.
 *
 * ATDD: estos tests deben FALLAR mientras el SUT no esté implementado.
 */
@RunWith(AndroidJUnit4::class)
class ModificarCuentaAT {

    private lateinit var servicioCuenta: CuentaServicio

    @Before
    fun setUp() {
        // Dependencia REAL (Firebase) → prueba de aceptación extremo a extremo
        servicioCuenta = CuentaServicio(FirebaseCuentaRepo())
    }

    // ---------- HU05-E01 (Válido: Modificación exitosa) ----------
    @Test
    fun HU05_E01() {
        runBlocking {
            // GIVEN: Sesión activa, formato válido, sin duplicados y conexión a BBDD.
            val emailActual = "rayanD@gmail.com"
            val nuevoEmail = "rayan_nuevo@gmail.com"
            val sesionActiva = true
            val conexionBBDD = true

            // WHEN: Se confirman los cambios.
            val resultado = servicioCuenta.modificarDatos(emailActual, nuevoEmail, sesionActiva, conexionBBDD)

            // THEN: El sistema actualiza la información y guarda los cambios.
            assertTrue(resultado)
        }
    }

    // ---------- HU05-E02 (Inválido: Correo ya existe / Duplicado) ----------
    @Test(expected = DuplicateEntityException::class)
    fun HU05_E02() {
        runBlocking {
            // GIVEN: El usuario tiene sesión activa, pero el correo ya pertenece a otro usuario.
            val emailActual = "rayanD@gmail.com"
            val nuevoEmail = "taliaH@gmail.com" // Correo en uso.
            val sesionActiva = true
            val conexionBBDD = true

            // WHEN: Intenta modificar su correo.
            // THEN: Lanza DuplicateEntityException y deniega el cambio.
            servicioCuenta.modificarDatos(emailActual, nuevoEmail, sesionActiva, conexionBBDD)
        }
    }

    // ---------- HU05-E03 (Inválido: Formato de datos no válido) ----------
    @Test(expected = InvalidFormatException::class)
    fun HU05_E03() {
        runBlocking {
            // GIVEN: El usuario intenta guardar la actualización introduciendo datos con un formato no válido.
            val emailActual = "rayanD@gmail.com"
            val nuevoEmail = "correo-invalido-sin-arroba"
            val sesionActiva = true
            val conexionBBDD = true

            // WHEN / THEN: Lanza InvalidFormatException
            servicioCuenta.modificarDatos(emailActual, nuevoEmail, sesionActiva, conexionBBDD)
        }
    }

    // ---------- HU05-E04 (Inválido: Fallo de conexión a BBDD) ----------
    @Test(expected = DatabaseConnectionException::class)
    fun HU05_E04() {
        runBlocking {
            // GIVEN: El sistema no logra conectarse con la base de datos.
            val emailActual = "rayanD@gmail.com"
            val nuevoEmail = "rayan_nuevo@gmail.com"
            val sesionActiva = true
            val conexionBBDD = false

            // WHEN / THEN: Lanza DatabaseConnectionException
            servicioCuenta.modificarDatos(emailActual, nuevoEmail, sesionActiva, conexionBBDD)
        }
    }

    // ---------- HU05-E05 (Inválido: Sin sesión activa) ----------
    @Test(expected = SessionNotActiveException::class)
    fun HU05_E05() {
        runBlocking {
            // GIVEN: Se solicita la modificación sin contar con una sesión activa.
            val emailActual = "rayanD@gmail.com"
            val nuevoEmail = "rayan_nuevo@gmail.com"
            val sesionActiva = false
            val conexionBBDD = true

            // WHEN / THEN: Lanza SessionNotActiveException
            servicioCuenta.modificarDatos(emailActual, nuevoEmail, sesionActiva, conexionBBDD)
        }
    }
}