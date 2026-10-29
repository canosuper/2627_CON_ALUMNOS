package com.example.tresenrayaapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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

    fun jugar(posicion: Int) {

//        //claúsula de guardia. Así evitamos anidamientos si hay más condiciones, ya que evalúas al principio y sales si no se quiere seguir.
//        if (tablero[posicion] != 0) return
//
//        tablero[posicion] = turno
//        if (turno == 1) {
//            turno = 2
//        } else {
//            turno = 1
//        }

        if (resultado != 0 || tablero[posicion] != 0) return
        tablero[posicion] = turno
        val linea = buscarLineaGanadora(tablero)
        if (linea.isNotEmpty()) {
            lineaGanadora = linea
            resultado = turno
            return
        }
        var lleno = true
        for (casilla in tablero) {
            if (casilla == 0) lleno = false
        }
        if (lleno) {
            resultado = 3
            return
        }
        if (turno == 1) turno = 2 else turno = 1
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            Text("Tres en raya de Halloween", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text("🎃 Calabaza contra 👻 Fantasma")

            Spacer(modifier = Modifier.height(12.dp))
            //Text(if (turno == 1) "Turno de 🎃" else "Turno de 👻")
            Text(textoEstado(resultado, turno), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            for (fila in 0..2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    for (columna in 0..2) {
                        val posicion = fila * 3 + columna
                        OutlinedButton(
                            onClick = { jugar(posicion) },
                            modifier = Modifier.size(100.dp)
                        ) {
                            Text(simbolo(tablero[posicion]), fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    }
}
