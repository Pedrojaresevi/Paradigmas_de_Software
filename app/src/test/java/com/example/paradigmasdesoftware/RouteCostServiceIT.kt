package com.example.paradigmasdesoftware

import com.example.paradigmasdesoftware.exceptions.FuelPriceNotAvailableException
import com.example.paradigmasdesoftware.service.FuelPriceService
import com.example.paradigmasdesoftware.service.RouteCostService
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class RouteCostServiceIT {
    private lateinit var fuelPriceServiceMock: FuelPriceService
    private lateinit var routeCostService: RouteCostService

    @Before
    fun setUp() {
        // 1. Instanciamos el mock en lugar del servicio de Minetur real
        fuelPriceServiceMock = mockk()
        // 2. Inyectamos el mock en la lógica de negocio
        routeCostService = RouteCostService(fuelPriceServiceMock)
    }

    // ---------- Escenario Válido (Comprobación de valor exacto) ----------
    @Test
    fun shouldCalculateExactCostWithMockedPrice() {
        // GIVEN: Forzamos a la API simulada a devolver exactamente 1.50 €/l
        every {
            fuelPriceServiceMock.getGasolinePricePerLiter("12", "Castellón")
        } returns 1.50

        // WHEN: Calculamos el coste para 75 km a 6.5 l/100km
        val result = routeCostService.calculateFuelCost(75.0, 6.5, "12", "Castellón")

        // THEN: El coste total debe ser exactamente 7.3125 € (75/100 * 6.5 * 1.50)
        assertEquals(1.50, result.fuelPricePerLiter, 0.0)
        assertEquals(7.3125, result.totalCost, 0.0001)
    }

    // ---------- Escenario Inválido (Simulación de caída de API) ----------
    @Test
    fun shouldThrowExceptionWhenApiFails() {
        // GIVEN: Forzamos al mock a lanzar la excepción que tienes en tu carpeta exceptions
        every {
            fuelPriceServiceMock.getGasolinePricePerLiter(any(), any())
        } throws FuelPriceNotAvailableException("Simulación de API caída")

        // WHEN & THEN: Aseguramos que la excepción sube correctamente
        assertThrows(FuelPriceNotAvailableException::class.java) {
            routeCostService.calculateFuelCost(75.0, 6.5, "12", "Castellón")
        }
    }
}