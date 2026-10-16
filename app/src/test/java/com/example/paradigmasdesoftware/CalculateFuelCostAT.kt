package com.example.paradigmasdesoftware

import com.example.paradigmasdesoftware.service.FuelPriceService
import com.example.paradigmasdesoftware.service.FuelPriceServiceMinetur
import com.example.paradigmasdesoftware.service.RouteCostService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalculateFuelCostAT {

    private lateinit var fuelPriceService: FuelPriceService
    private lateinit var routeCostService: RouteCostService

    @Before
    fun setUp() {
        // Dependencia REAL (prueba de aceptación = extremo a extremo)
        fuelPriceService = FuelPriceServiceMinetur()
        // Inyección de la dependencia en el servicio bajo test
        routeCostService = RouteCostService(fuelPriceService)
    }

    // ---------- Escenario válido ----------
    @Test
    fun shouldReturnPlausibleCostWhenRouteIsValid() {
        // GIVEN una ruta de 75 km, un coche que consume 6.5 l/100km y un municipio con gasolineras
        val distanceKm = 75.0
        val consumption = 6.5
        val provinceCode = "12"
        val municipality = "Castellón"

        // WHEN calculo el coste de combustible de la ruta
        val result = routeCostService.calculateFuelCost(
            distanceKm, consumption, provinceCode, municipality
        )

        // THEN el precio es verosímil y el coste sigue la fórmula
        assertTrue(
            "Precio fuera de rango verosímil: ${result.fuelPricePerLiter}",
            result.fuelPricePerLiter in 0.8..3.0
        )
        val expectedCost = distanceKm / 100.0 * consumption * result.fuelPricePerLiter
        assertEquals(expectedCost, result.totalCost, 0.01)
        assertTrue(result.totalCost > 0.0)
    }

    // ---------- Escenario inválido ----------
    @Test
    fun shouldThrowIllegalArgumentExceptionWhenDistanceIsNegative() {
        // GIVEN una distancia negativa
        val distanceKm = -10.0

        // WHEN & THEN se rechaza la petición con IllegalArgumentException
        assertThrows(IllegalArgumentException::class.java) {
            routeCostService.calculateFuelCost(distanceKm, 6.5, "12", "Castellón")
        }
    }
}