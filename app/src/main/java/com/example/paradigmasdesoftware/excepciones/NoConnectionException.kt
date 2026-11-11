package com.example.paradigmasdesoftware.excepciones

class NoConnectionException(
    message: String = "No hay conexión con la base de datos",
    cause: Throwable? = null
) : Exception(message, cause)
