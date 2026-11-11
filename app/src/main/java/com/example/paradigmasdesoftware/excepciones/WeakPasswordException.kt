package com.example.paradigmasdesoftware.excepciones

class WeakPasswordException(
    message: String = "La contraseña debe tener al menos 6 caracteres",
    cause: Throwable? = null
) : Exception(message, cause)
