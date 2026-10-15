package com.example.paradigmasdesoftware

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val origen = "Castellón"
            val destino = "Valencia"
            val distanciaKm = 75.0

            var resultadoText by remember { mutableStateOf("") }
            var cargando by remember { mutableStateOf(false) }

            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Ruta",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = origen,
                    onValueChange = {},
                    label = { Text("Origen") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = destino,
                    onValueChange = {},
                    label = { Text("Destino") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Distancia: $distanciaKm km",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        cargando = true
                        resultadoText = ""

                        coroutineScope.launch {
                            val resultado = conectarYCalcularCosteManual(distanciaKm)
                            resultadoText = resultado
                            cargando = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Calcular coste", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (cargando) {
                    CircularProgressIndicator()
                } else if (resultadoText.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = resultadoText,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    private suspend fun conectarYCalcularCosteManual(distanciaKm: Double): String = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/EstacionesTerrestres/FiltroMunicipio/1813"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            // 1. Obtenemos el texto crudo de la respuesta
            val jsonStringCrudo = connection.inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()

            // 2. Crear la carpeta "ficheros_intercambio" si no existe
            val carpeta = File(filesDir, "ficheros_intercambio")
            if (!carpeta.exists()) {
                carpeta.mkdirs()
            }

            // 3. Guardar el archivo JSON en esa carpeta
            val archivoJson = File(carpeta, "respuesta_carburantes.json")
            archivoJson.writeText(jsonStringCrudo)

            // 4. Extracción manual de los datos del JSON
            val jsonObjectRoot = JSONObject(jsonStringCrudo)
            val listaGasolineras = jsonObjectRoot.getJSONArray("ListaEESSPrecio")

            var precioLitroEncontrado: Double? = null

            for (i in 0 until listaGasolineras.length()) {
                val gasolinera = listaGasolineras.getJSONObject(i)
                val precioString = gasolinera.optString("Precio Gasolina 95 E5", "")

                if (precioString.isNotBlank()) {
                    precioLitroEncontrado = precioString.replace(",", ".").toDoubleOrNull()
                    if (precioLitroEncontrado != null) break
                }
            }

            if (precioLitroEncontrado != null) {
                val consumoMedio = 6.5
                val costeTotal = (distanciaKm / 100.0) * consumoMedio * precioLitroEncontrado

                "Precio combustible: $precioLitroEncontrado €/l\n" +
                        "Consumo estimado: $consumoMedio l/100km\n\n" +
                        "Coste estimado: ${String.format("%.2f", costeTotal)} €\n\n" +
                        "📁 Fichero guardado en:\n${archivoJson.name}"
            } else {
                "No se encontró el precio de la gasolina."
            }

        } catch (e: Exception) {
            "Error de conexión: ${e.localizedMessage}"
        }
    }
}