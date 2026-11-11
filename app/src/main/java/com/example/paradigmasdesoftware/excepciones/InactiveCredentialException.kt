package com.example.paradigmasdesoftware.excepciones

class InactiveCredentialException(
    message: String = "La credencial de la sesión no está activa",
    cause: Throwable? = null
) : Exception(message, cause)
