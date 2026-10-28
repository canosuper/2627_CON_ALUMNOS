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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PantallaPrincipal() {
    val tablero = remember {
        mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        //Forma más avanzada: mutableStateListOf(*Array(9) { 0 })
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            Text("Tres en raya de Halloween", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text("🎃 Calabaza contra 👻 Fantasma")

            Spacer(modifier = Modifier.height(24.dp))

            for (fila in 0..2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    for (columna in 0..2) {
                        val posicion = fila * 3 + columna
                        OutlinedButton(
                            onClick = { tablero[posicion] = if (tablero[posicion] == 1) 2 else 1  },
                            modifier = Modifier.size(82.dp)
                        ) {
                            Text(simbolo(tablero[posicion]), fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    }
}
