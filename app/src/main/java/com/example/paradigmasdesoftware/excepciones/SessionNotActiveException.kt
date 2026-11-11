package com.example.paradigmasdesoftware.excepciones

class SessionNotActiveException(
    message: String = "No hay una sesión activa para realizar esta acción",
    cause: Throwable? = null
) : Exception(message, cause)