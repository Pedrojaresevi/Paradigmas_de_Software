package com.example.paradigmasdesoftware.excepciones

class InvalidFormatException(
    message: String = "El formato de los datos introducidos no es válido",
    cause: Throwable? = null
) : Exception(message, cause)