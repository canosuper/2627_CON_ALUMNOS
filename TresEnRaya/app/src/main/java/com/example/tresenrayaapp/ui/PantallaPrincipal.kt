package com.example.tresenrayaapp.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.tresenrayaapp.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenrayaapp.juego.buscarLineaGanadora


@Composable
fun PantallaPrincipal() {
    val tablero = remember {
        mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        //Forma más avanzada: mutableStateListOf(*Array(9) { 0 })
    }
    var turno by remember { mutableIntStateOf(1) }
    // 0 = jugando; 1 = gana 🎃; 2 = gana 👻; 3 = empate.
    var resultado by remember { mutableIntStateOf(0) }
    var lineaGanadora by remember {
        mutableStateOf<List<Int>>(emptyList())
    }
    var victoriasCalabaza by remember { mutableIntStateOf(0) }
    var victoriasFantasma by remember { mutableIntStateOf(0) }
    var empates by remember { mutableIntStateOf(0) }
    val contexto = LocalContext.current

    // Devuelve 0 si no termina; 1, 2 o 3 si acaba de terminar.
    fun jugar(posicion: Int): Int {
        if (resultado != 0 || tablero[posicion] != 0) return 0
        tablero[posicion] = turno
        val linea = buscarLineaGanadora(tablero)
        if (linea.isNotEmpty()) {
            lineaGanadora = linea
            resultado = turno
            if (turno == 1) victoriasCalabaza++
            else victoriasFantasma++
            return resultado
        }
        var lleno = true
        for (casilla in tablero) {
            if (casilla == 0) lleno = false
        }
        if (lleno) {
            resultado = 3
            empates++
            return resultado
        }
        if (turno == 1) turno = 2 else turno = 1
        return 0
    }
    fun nuevaPartida() {
        for (i in 0..8) tablero[i] = 0
        turno = 1
        resultado = 0
        lineaGanadora = emptyList()
    }
    fun reiniciarTodo() {
        nuevaPartida()
        victoriasCalabaza = 0
        victoriasFantasma = 0
        empates = 0
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            Text("Tres en raya de Halloween", fontSize = 24.sp)
            Image(
                painter = painterResource(R.drawable.cabecera_halloween),
                contentDescription = "Calabazas y fantasmas jugando al tres en raya",
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("🎃 Calabaza contra 👻 Fantasma")

            Spacer(modifier = Modifier.height(12.dp))
            //Text(if (turno == 1) "Turno de 🎃" else "Turno de 👻")
            Text(textoEstado(resultado, turno), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text("🎃 $victoriasCalabaza 👻 $victoriasFantasma Empates: $empates")
            Spacer(modifier = Modifier.height(12.dp))

            for (fila in 0..2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    for (columna in 0..2) {
                        val posicion = fila * 3 + columna
                        val esGanadora = lineaGanadora.contains(posicion)
                        val fondo = if (esGanadora) Color(0xFFFFD180) else Color(0xFFF4EFFA)
                        OutlinedButton(
                            onClick = {
                                val fin = jugar(posicion)
                                if (fin != 0) {
                                    Toast.makeText(
                                        contexto, textoEstado(fin, turno), Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            enabled = resultado == 0 && tablero[posicion] == 0,
                            modifier = Modifier.size(82.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = fondo,
                                disabledContainerColor = fondo,
                                contentColor = Color(0xFF40264B),
                                disabledContentColor = Color(0xFF40264B)
                            )
                        ) {
                            Text(simbolo(tablero[posicion]), fontSize = 30.sp)
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { nuevaPartida() }) { Text("Nueva partida") }
                Button(onClick = { reiniciarTodo() }) { Text("Reiniciar todo") }
            }
        }
    }
}
