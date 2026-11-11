package com.example.paradigmasdesoftware.excepciones

class InvalidVehicleDataException(
    message: String = "Los datos del vehículo no tienen un formato válido",
    cause: Throwable? = null
) : Exception(message, cause)
