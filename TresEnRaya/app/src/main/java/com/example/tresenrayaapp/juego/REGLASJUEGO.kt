package com.example.tresenrayaapp.juego

// Kotlin normal: no hay nada de Compose en este archivo.
fun buscarLineaGanadora(tablero: List<Int>): List<Int> {
    val lineas = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
        listOf(0, 4, 8), listOf(2, 4, 6)
    )
    for (linea in lineas) {
        val primero = tablero[linea[0]]
        if (primero != 0 &&
            primero == tablero[linea[1]] &&
            primero == tablero[linea[2]]) {
            return linea
        }
    }
    return emptyList()
}

fun buscarLineaGanadoraClasica(tablero: List<Int>): List<Int> {
    // --- FILAS HORIZONTALES ---
    if (tablero[0] != 0 && tablero[0] == tablero[1] && tablero[1] == tablero[2]) return listOf(0, 1, 2)
    if (tablero[3] != 0 && tablero[3] == tablero[4] && tablero[4] == tablero[5]) return listOf(3, 4, 5)
    if (tablero[6] != 0 && tablero[6] == tablero[7] && tablero[7] == tablero[8]) return listOf(6, 7, 8)

    // --- COLUMNAS VERTICALES ---
    if (tablero[0] != 0 && tablero[0] == tablero[3] && tablero[3] == tablero[6]) return listOf(0, 3, 6)
    if (tablero[1] != 0 && tablero[1] == tablero[4] && tablero[4] == tablero[7]) return listOf(1, 4, 7)
    if (tablero[2] != 0 && tablero[2] == tablero[5] && tablero[5] == tablero[8]) return listOf(2, 5, 8)

    // --- DIAGONALES ---
    if (tablero[0] != 0 && tablero[0] == tablero[4] && tablero[4] == tablero[8]) return listOf(0, 4, 8)
    if (tablero[2] != 0 && tablero[2] == tablero[4] && tablero[4] == tablero[6]) return listOf(2, 4, 6)

    // Si nadie ha ganado todavía
    return emptyList()
}