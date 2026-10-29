package com.example.tresenrayaapp.ui

fun simbolo(valor: Int): String {
    if (valor == 1) return "🎃"
    if (valor == 2) return "👻"
    return " "
}

// Devuelve un texto calculado a partir del estado existente.
fun textoEstado(resultado: Int, turno: Int): String {
    if (resultado == 1) return "¡Han ganado las calabazas!"
    if (resultado == 2) return "¡Han ganado los fantasmas!"
    if (resultado == 3) return "¡Empate!"
    if (turno == 1) return "Turno de 🎃"
    return "Turno de 👻"
}