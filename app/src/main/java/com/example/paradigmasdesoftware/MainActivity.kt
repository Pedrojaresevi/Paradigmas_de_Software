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
import com.example.paradigmasdesoftware.service.FuelPriceServiceMinetur
import com.example.paradigmasdesoftware.service.RouteCostService

class MainActivity : ComponentActivity() {
    private val routeCostService = RouteCostService(FuelPriceServiceMinetur())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val origen = "Castellón de la Plana"
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

    private suspend fun conectarYCalcularCosteManual(distanciaKm: Double): String =
        withContext(Dispatchers.IO) {
            try {
                val consumoMedio = 6.5
                val result = routeCostService.calculateFuelCost(
                    distanciaKm, consumoMedio, "12", "Castellón"
                )
                "Precio combustible: ${result.fuelPricePerLiter} €/l\n" +
                        "Consumo estimado: $consumoMedio l/100km\n\n" +
                        "Coste estimado: ${String.format("%.2f", result.totalCost)} €"
            } catch (e: Exception) {
                "Error: ${e.localizedMessage}"
            }
        }
}