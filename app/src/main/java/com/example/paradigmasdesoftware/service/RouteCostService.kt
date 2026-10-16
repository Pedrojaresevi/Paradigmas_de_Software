package com.example.paradigmasdesoftware.service

import com.example.paradigmasdesoftware.exceptions.FuelPriceNotAvailableException
import com.example.paradigmasdesoftware.model.FuelCost

class RouteCostService(private val fuelPriceService: FuelPriceService) {

    @Throws(FuelPriceNotAvailableException::class)
    fun calculateFuelCost(
        distanceKm: Double,
        consumptionLPer100Km: Double,
        provinceCode: String,
        municipality: String
    ): FuelCost {
        require(distanceKm >= 0.0) { "La distancia no puede ser negativa: $distanceKm" }
        require(consumptionLPer100Km > 0.0) { "El consumo debe ser mayor que 0: $consumptionLPer100Km" }

        val pricePerLiter = fuelPriceService.getGasolinePricePerLiter(provinceCode, municipality)
        val totalCost = distanceKm / 100.0 * consumptionLPer100Km * pricePerLiter
        return FuelCost(pricePerLiter, totalCost)
    }
}