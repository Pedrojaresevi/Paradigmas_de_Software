
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InvalidCredentialsException(message: String) : Exception(message)
class InvalidEmailFormatException(message: String) : Exception(message)
class UserNotFoundException(message: String) : Exception(message)

class HU02_IniciarSesionTest {

    private lateinit var authService: AuthService

    @Before
    fun setUp() {
        // Inicializamos el servicio antes de cada test
        authService = AuthService()
        // GIVEN general: Conexión simulada a BBDD establecida
    }

    @Test
    fun HU02_E01_InicioSesion_Valido() {
        // GIVEN: El usuario existe en BBDD con credenciales correctas.
        val email = "rayanD@gmail.com"
        val password = "PasswordSegura123!"

        // WHEN: Intenta iniciar sesión con sus credenciales correctas.
        val result = authService.login(email, password)

        // THEN: Se validan, devuelve true y se activa la sesión.
        assertTrue("El inicio de sesión debe ser exitoso", result)
        assertEquals("La sesión debe coincidir con el usuario logueado", email, authService.activeSessionEmail)
    }

    @Test
    fun HU02_E02_InicioSesion_ContrasenaInvalida() {
        // GIVEN: El usuario existe, pero va a usar una contraseña incorrecta.
        val email = "rayanD@gmail.com"
        val invalidPassword = "1234"

        // WHEN / THEN: Al iniciar sesión, se lanza InvalidCredentialsException y no hay sesión.
        assertThrows(InvalidCredentialsException::class.java) {
            authService.login(email, invalidPassword)
        }

        assertNull("La sesión no debe activarse con contraseña errónea", authService.activeSessionEmail)
    }

    @Test
    fun HU02_E03_InicioSesion_FormatoCorreoInvalido() {
        // GIVEN: Un intento de login con un email mal escrito (sin el '@').
        val invalidEmailFormat = "rayanDgmail.com"
        val password = "PasswordSegura123!"

        // WHEN / THEN: El sistema frena la autenticación antes de buscar en BBDD y lanza InvalidEmailFormatException.
        assertThrows(InvalidEmailFormatException::class.java) {
            authService.login(invalidEmailFormat, password)
        }

        assertNull("La sesión no debe activarse con formato de email inválido", authService.activeSessionEmail)
    }

    @Test
    fun HU02_E04_InicioSesion_UsuarioNoRegistrado() {
        // GIVEN: El email introducido tiene formato correcto pero NO existe en el sistema.
        val unregisteredEmail = "noexiste@gmail.com"
        val password = "PasswordSegura123!"

        // WHEN / THEN: El sistema consulta, no lo encuentra y lanza UserNotFoundException.
        assertThrows(UserNotFoundException::class.java) {
            authService.login(unregisteredEmail, password)
        }

        assertNull("La sesión no debe activarse si el usuario no existe", authService.activeSessionEmail)
    }
}