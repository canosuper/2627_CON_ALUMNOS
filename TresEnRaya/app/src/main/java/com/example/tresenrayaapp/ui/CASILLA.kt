package com.example.tresenrayaapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun Casilla(
    valor: Int,
    esGanadora: Boolean,
    habilitada: Boolean,
    onPulsar: () -> Unit
) {
    val fondo = if (esGanadora) Color(0xFFFFD180) else Color(0xFFF4EFFA)
    OutlinedButton(
        onClick = onPulsar,
        enabled = habilitada,
        modifier = Modifier.size(82.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = fondo,
            disabledContainerColor = fondo,
            contentColor = Color(0xFF40264B),
            disabledContentColor = Color(0xFF40264B)
        )
    ) {
        Text(text = simbolo(valor), fontSize = 30.sp)
    }
}