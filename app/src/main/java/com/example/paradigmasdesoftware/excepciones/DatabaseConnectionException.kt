package com.example.paradigmasdesoftware.excepciones

class DatabaseConnectionException(
    message: String = "Fallo de conexión con la base de datos",
    cause: Throwable? = null
) : Exception(message, cause)