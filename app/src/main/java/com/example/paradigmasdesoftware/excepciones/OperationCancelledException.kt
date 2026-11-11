package com.example.paradigmasdesoftware.excepciones

class OperationCancelledException(
    message: String = "Operación cancelada: No se aceptó la confirmación de borrado",
    cause: Throwable? = null
) : Exception(message, cause)