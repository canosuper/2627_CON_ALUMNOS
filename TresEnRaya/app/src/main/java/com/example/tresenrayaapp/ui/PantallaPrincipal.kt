package com.example.tresenrayaapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            Text("Tres en raya de Halloween", fontSize = 24.sp)
            Text("🎃 Calabaza contra 👻 Fantasma")

            Text("Posición cero: ${tablero[0]}")
            //Button(onClick = { tablero[0] = 1 }) {
            Button(onClick = { tablero[0] = 1 - tablero[0]})
            {
                Text("Cambiar posición ")
            }
        }
    }
}
