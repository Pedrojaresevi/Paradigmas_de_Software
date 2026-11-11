package com.example.paradigmasdesoftware.excepciones

class UserAlreadyExistsException(
    message: String = "El usuario ya está registrado",
    cause: Throwable? = null
) : Exception(message, cause)
