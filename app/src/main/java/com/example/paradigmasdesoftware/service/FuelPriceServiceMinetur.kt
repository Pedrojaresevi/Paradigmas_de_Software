package com.example.paradigmasdesoftware.service

import com.example.paradigmasdesoftware.exceptions.FuelPriceNotAvailableException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class FuelPriceServiceMinetur : FuelPriceService {

    override fun getGasolinePricePerLiter(provinceCode: String, municipality: String): Double {
        try {
            val url = URL(
                "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/" +
                        "PreciosCarburantes/EstacionesTerrestres/FiltroProvincia/$provinceCode"
            )
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val rawJson = connection.inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()

            val stations = JSONObject(rawJson).getJSONArray("ListaEESSPrecio")

            // Primero: estaciones del municipio pedido
            for (i in 0 until stations.length()) {
                val station = stations.getJSONObject(i)
                if (station.optString("Municipio", "").contains(municipality, ignoreCase = true)) {
                    parsePrice(station.optString("Precio Gasolina 95 E5", ""))?.let { return it }
                }
            }
            // Respaldo: cualquier estación de la provincia con precio
            for (i in 0 until stations.length()) {
                val station = stations.getJSONObject(i)
                parsePrice(station.optString("Precio Gasolina 95 E5", ""))?.let { return it }
            }

            throw FuelPriceNotAvailableException("No hay precios de gasolina para la provincia $provinceCode")
        } catch (e: FuelPriceNotAvailableException) {
            throw e
        } catch (e: Exception) {
            throw FuelPriceNotAvailableException("Error al consultar el precio del combustible", e)
        }
    }

    private fun parsePrice(raw: String): Double? =
        if (raw.isBlank()) null else raw.replace(",", ".").toDoubleOrNull()
}