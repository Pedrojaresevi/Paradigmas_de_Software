package com.example.paradigmasdesoftware.excepciones

class UserNotFoundException(
    message: String = "No existe ningún usuario registrado con ese email",
    cause: Throwable? = null
) : Exception(message, cause)
