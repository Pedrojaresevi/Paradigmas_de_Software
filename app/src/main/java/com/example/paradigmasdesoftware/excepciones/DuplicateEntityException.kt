package com.example.paradigmasdesoftware.excepciones

class DuplicateEntityException(
    message: String = "El correo o usuario introducido ya existe registrado en la BBDD",
    cause: Throwable? = null
) : Exception(message, cause)