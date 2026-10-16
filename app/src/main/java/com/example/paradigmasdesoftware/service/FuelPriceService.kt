package com.example.paradigmasdesoftware.service

import com.example.paradigmasdesoftware.exceptions.FuelPriceNotAvailableException

interface FuelPriceService {
    @Throws(FuelPriceNotAvailableException::class)
    fun getGasolinePricePerLiter(provinceCode: String, municipality: String): Double
}