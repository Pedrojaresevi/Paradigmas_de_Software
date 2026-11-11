package com.example.paradigmasdesoftware.excepciones

class RouteNotAvailableException(
    message: String = "La ruta o la distancia no están disponibles para calcular el precio",
    cause: Throwable? = null
) : Exception(message, cause)
