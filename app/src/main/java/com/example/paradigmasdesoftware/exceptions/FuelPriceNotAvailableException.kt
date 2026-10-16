package com.example.paradigmasdesoftware.exceptions

class FuelPriceNotAvailableException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)