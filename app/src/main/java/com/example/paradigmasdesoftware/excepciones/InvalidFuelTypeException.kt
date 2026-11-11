package com.example.paradigmasdesoftware.excepciones

class InvalidFuelTypeException(
    message: String = "El tipo de combustible indicado no es válido",
    cause: Throwable? = null
) : Exception(message, cause)
