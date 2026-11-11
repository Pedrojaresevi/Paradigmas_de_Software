package com.example.paradigmasdesoftware.excepciones

class WrongPasswordException(
    message: String = "La contraseña es incorrecta",
    cause: Throwable? = null
) : Exception(message, cause)
