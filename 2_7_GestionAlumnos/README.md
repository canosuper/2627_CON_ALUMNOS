# 2_7_GestionAlumnos - Guía Didáctica y Resumen del Proyecto

Este proyecto es una aplicación desarrollada en **Android con Jetpack Compose** para la asignatura de Desarrollo de Interfaces / Programación Multimedia. Sirve como práctica guiada paso a paso sobre el manejo de **Estado, Recomposición, Formularios, Validaciones y Colecciones Reactivas**.

---

## 📚 Índice de Contenidos

1. [Conceptos Teóricos Clave de Jetpack Compose](#1-conceptos-teóricos-clave-de-jetpack-compose)
2. [Paso a Paso de las Prácticas e Implementación](#2-paso-a-paso-de-las-prácticas-e-implementación)
3. [Estructura del Código Final](#3-estructura-del-código-final)
4. [Resumen de Buenas Prácticas Pedagógicas](#4-resumen-de-buenas-prácticas-pedagógicas)

---

## 1. Conceptos Teóricos Clave de Jetpack Compose

### 🔄 La Fórmula Mágica: `UI = f(Estado)`
En Jetpack Compose la interfaz no se manipula directamente (`findViewById` ya no existe). La interfaz de pantalla es un **reflejo automático de las variables de estado**. Al modificar los datos de una variable de estado, Compose vuelve a ejecutar la UI para mostrar el nuevo valor.

### 🔔 `mutableStateOf` (La Alarma)
Crea una variable observable. Cuando su valor cambia, manda una señal al motor de Compose para avisar de que las funciones `@Composable` que leen esa variable deben **recomponerse** (redibujarse).

### 🧠 `remember` (El Almacén de Memoria)
Una función `@Composable` se vuelve a ejecutar desde el principio en cada recomposición. Sin `remember`, una variable como `var contador = mutableStateOf(0)` se volvería a inicializar a `0` en cada redibujado. 
* `remember` guarda el valor en el árbol de composición (*Slot Table*) para que **no se pierda ni se reinicie** entre ejecuciones.

### ⚡ Recomposición Inteligente (*Smart Recomposition*)
Compose no redibuja toda la Activity ni toda la pantalla. Solo vuelve a ejecutar el ámbito más pequeño que **lee** la variable modificada. Si un elemento de la pantalla no ha cambiado, Compose se lo salta (*skip*).

### 📝 Formulario Controlado y Limpieza de Cajas
Las cajas de texto `TextField` leen su valor directamente de una variable de estado (`value = nombre`). Al guardar un alumno y hacer `nombre = ""`, la caja se limpia sola de forma automática porque su estado pasa a estar vacío.

### 🛡️ Teclado y Filtrado Numérico
* `KeyboardOptions(keyboardType = KeyboardType.Number)`: Muestra el teclado numérico en el teléfono al pulsar la caja.
* `if (it.all { c -> c.isDigit() })`: Asegura que solo se almacenen dígitos (0-9), protegiendo la app incluso si el usuario pega texto desde el portapapeles.
* `edadTexto.toIntOrNull()`: Convierte el texto a `Int` de forma segura. Devuelve `null` si está vacío o es inválido, evitando cierres inesperados (*crashes* por `NumberFormatException`).

### 📦 Colecciones Reactivas: `toMutableStateList()`
Una `ArrayList` estándar de Kotlin no avisa a Compose cuando se añade un elemento con `.add()`. 
Al usar `remember { alumnos.toMutableStateList() }`, se crea una `SnapshotStateList` que notifica automáticamente a los componentes como `LazyColumn` cuando se añade o elimina un elemento.

### 📜 `LazyColumn` vs `RecyclerView`
* **Rendimiento**: Dibuja únicamente las tarjetas visibles en pantalla (reciclando memoria al hacer scroll).
* **Simplicidad**: Reemplaza al antiguo `RecyclerView` (que requería `Adapter`, `ViewHolder` y más de 80 líneas) en apenas 5 líneas de código declarativo.

### 🔤 Plantillas de Cadenas en Kotlin (`$` vs `${}`)
* **Variables simples**: Se usa `$variable` (p. ej. `"$nombre"`).
* **Propiedades de objetos con punto (`.`)**: Se requiere usar `${objeto.propiedad}` (p. ej. `"${alumno.nombre}"`). Sin las llaves, Kotlin imprime el objeto entero seguido del texto literal `.nombre`.
* **Universalidad**: `${variable}` funciona siempre, tanto para variables simples como compuestas.

---

## 2. Paso a Paso de las Prácticas e Implementación

### Práctica 1: Modificar el contador
* Creación de botones dentro de una `Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)`.
* Adición del botón "Quitar" con validación `if (numeroAlumnos > 0)` para impedir valores negativos.

### Sección 4 & 5: Introducir datos del alumno y completar el formulario
* Creación de campos de texto independientes con `TextField`: `nombre`, `ciudad` y `edadTexto`.
* Espaciado adecuado con `Spacer(modifier = Modifier.height(8.dp))`.

### Sección 6 & Práctica 2: Validación de datos y campos obligatorios
* Conversión segura con `.toIntOrNull()`.
* Condición combinada: `if (nombre.isBlank() || ciudad.isBlank() || edad == null)` para mostrar el mensaje de error `"Completa todos los datos"` en rojo (`Color.Red`).
* Si los datos son válidos, muestra la información del alumno e indica si es "Mayor de edad" o "Menor de edad".

### Sección 7 & 8: Data Class y componente reutilizable `AlumnoCard`
* Creación del modelo de datos `data class Alumno(val nombre: String, val ciudad: String, val edad: Int, val curso: String = "SIN ASIGNAR")`.
* Creación de la tarjeta con `Card` y `Column` en el componente `@Composable fun AlumnoCard(alumno: Alumno)`.

### Práctica 3: Personalizar la tarjeta
* Incorporación del campo de ciclo/curso (`curso = "DAM2T"`) en la tarjeta y en la `data class`.
* Ajuste de espaciados internos con `Spacer` para legibilidad.

### Sección 9, 10 & Funcionalidad Final: Lista reactiva y `LazyColumn`
* Inicialización de la lista inicial con `alumnos = listOf(...)`.
* Conversión a lista reactiva con `val listaAlumnos = remember { alumnos.toMutableStateList() }`.
* Botón de guardar que instancia el nuevo `Alumno`, lo añade a `listaAlumnos.add(nuevoAlumno)` y limpia las cajas de texto (`nombre = ""`, `ciudad = ""`, `edadTexto = ""`).
* Despliegue dinámico de la lista mediante `@Composable fun ListaAlumnos(alumnos: List<Alumno>)` usando `LazyColumn` e `items(alumnos)`.

---

## 3. Estructura del Código Final (`MainActivity.kt`)

```kotlin
package com.maestre.a2_7_gestionalumnos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maestre.a2_7_gestionalumnos.ui.theme._2_7_GestionAlumnosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaPrincipal()
        }
    }
}

@Composable
fun PantallaPrincipal() {
    // 1. Lista reactiva con estado inicializada a partir de la lista base
    val listaAlumnos = remember { alumnos.toMutableStateList() }

    // 2. Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var edadTexto by remember { mutableStateOf("") }

    _2_7_GestionAlumnosTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Gestión de alumnos",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Añade y consulta los alumnos del grupo")
                Spacer(modifier = Modifier.height(16.dp))

                // Campo Nombre
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo Ciudad
                TextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo Edad con filtro de dígitos y teclado numérico
                TextField(
                    value = edadTexto,
                    onValueChange = {
                        if (it.all { c -> c.isDigit() }) {
                            edadTexto = it
                        }
                    },
                    label = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Validación y Guardado de Alumno
                val edad = edadTexto.toIntOrNull()

                if (nombre.isBlank() || ciudad.isBlank() || edad == null) {
                    Text(
                        text = "Completa todos los datos",
                        color = Color.Red
                    )
                } else {
                    Button(
                        onClick = {
                            val nuevoAlumno = Alumno(
                                nombre = nombre,
                                ciudad = ciudad,
                                edad = edad,
                                curso = "DAM2T"
                            )
                            listaAlumnos.add(nuevoAlumno)

                            // Limpieza de estados
                            nombre = ""
                            ciudad = ""
                            edadTexto = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Alumno")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Renderizado de la lista con LazyColumn
                ListaAlumnos(alumnos = listaAlumnos)
            }
        }
    }
}

// --- COMPONENTE TARJETA DE ALUMNO ---
@Composable
fun AlumnoCard(alumno: Alumno) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = alumno.nombre,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Alumno de: ${alumno.curso}",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text("Ciudad: ${alumno.ciudad}")
            Text("Edad: ${alumno.edad} años")
            Spacer(modifier = Modifier.height(4.dp))

            if (alumno.edad >= 18) {
                Text("Mayor de edad")
            } else {
                Text(
                    text = "Menor de edad",
                    color = Color.Red
                )
            }
        }
    }
}

// --- LISTA DESPLAZABLE CON LAZYCOLUMN ---
@Composable
fun ListaAlumnos(alumnos: List<Alumno>) {
    LazyColumn {
        items(alumnos) { alumno ->
            AlumnoCard(alumno = alumno)
        }
    }
}

// --- MODELO DE DATOS (DATA CLASS) ---
data class Alumno(
    val nombre: String,
    val ciudad: String,
    val edad: Int,
    val curso: String = "SIN ASIGNAR"
)

// --- LISTA BASE INICIAL ---
val alumnos = listOf(
    Alumno("Ana", "Ciudad Real", 20, "DAM2T"),
    Alumno("Luis", "Toledo", 17, "DAM2T"),
    Alumno("Marta", "Albacete", 19, "DAM2T"),
    Alumno("Pablo", "Cuenca", 21, "DAM2T"),
    Alumno("Sara", "Guadalajara", 18, "DAM2T")
)
```

---

## 4. Resumen de Buenas Prácticas Pedagógicas

1. **Enseñanza Incremental**: Comenzar modificando componentes simples (contador) para afianzar `mutableStateOf` y `remember`, pasar por formularios simples, incluir validaciones defensivas (`.toIntOrNull()`), modularizar en componentes (`AlumnoCard`) y culminar en listas dinámicas con `LazyColumn` y `toMutableStateList()`.
2. **Entender el 'Por qué' de las decisiones**:
   * Usar `.isBlank()` en lugar de `.isEmpty()` para considerar válidos solo textos con caracteres reales.
   * Usar `data class` para encapsular datos.
   * Usar `toMutableStateList()` para que las mutaciones de lista sean reactivas en Compose.
