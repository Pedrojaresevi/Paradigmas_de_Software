package com.example.paradigmasdesoftware.excepciones

class InvalidEmailException(
    message: String = "El email no tiene un formato válido",
    cause: Throwable? = null
) : Exception(message, cause)
