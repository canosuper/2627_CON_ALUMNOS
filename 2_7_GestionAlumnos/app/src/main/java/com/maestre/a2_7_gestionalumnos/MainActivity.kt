package com.maestre.a2_7_gestionalumnos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
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
    _2_7_GestionAlumnosTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
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

                /*
                // --- CÓDIGO DEL CONTADOR ANTERIOR (DEJADO COMENTADO) ---
                var numeroAlumnos by remember { mutableStateOf(0) }

                Text("Número de alumnos: $numeroAlumnos")

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { numeroAlumnos++ }
                    ) {
                        Text("Añadir")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            if (numeroAlumnos > 0) {
                                numeroAlumnos--
                            }
                        }
                    ) {
                        Text("Quitar")
                    }
                }
                */

                // --- 5. COMPLETAR EL FORMULARIO ---
                val listaAlumnos = remember { alumnos.toMutableStateList() }

                // Declaración de los tres estados para los datos del alumno
                var nombre by remember { mutableStateOf("") }
                var ciudad by remember { mutableStateOf("") }
                var edadTexto by remember { mutableStateOf("") }

                // Campo para el Nombre
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo para la Ciudad
                TextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo para la Edad (filtrando solo dígitos y mostrando teclado numérico)
                TextField(
                    value = edadTexto,
                    onValueChange = {
                        // Solo se guardará si TODOS los caracteres son dígitos (0-9)
                        if (it.all { c -> c.isDigit() }) {
                            edadTexto = it
                        }
                    },
                    label = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

//                // Resumen en tiempo real de los datos introducidos
//                Text("Datos introducidos:")
//                Text("• Nombre: $nombre")a
//                Text("• Ciudad: $ciudad")
//                Text("• Edad: $edadTexto")

                Spacer(modifier = Modifier.height(16.dp))

                // --- 6. COMPROBAR LOS DATOS INTRODUCIDOS ---
                val edad = edadTexto.toIntOrNull()

                // Comprobamos si falta algún dato (nombre o ciudad vacíos, o edad nula/inválida)
                if (nombre.isBlank() || ciudad.isBlank() || edad == null) {
                    Text(
                        text = "Completa todos los datos",
                        color = Color.Red
                    )
                } else {
                    // --- 7. INSTANCIAMOS EL OBJETO ALUMNO ---
//                    val alumno = Alumno(
//                        nombre = nombre,
//                        ciudad = ciudad,
//                        edad = edad,
//                        curso = "DAM2T"
//                    )
                    // Cuando los tres datos son correctos, mostramos la información completa
//                    Text("Datos introducidos:")
//                    Text("• Nombre: ${alumno.nombre}")
//                    Text("• Ciudad: ${alumno.ciudad}")
//                    Text("• Edad: ${alumno.edad}")

                    //aquí como estaba antes sin instanciar el objeto, esto ya sobraría.
                   // Text("• Nombre: $nombre")
                    //Text("• Ciudad: $ciudad")
                    //sText("• Edad: $edad")

//
                    //AlumnoCard(alumno = alumno)

                    //ListaAlumnos(alumnos)

                    // BOTÓN PARA GUARDAR Y AÑADIR A LA LISTA
                    Button(
                        onClick = {
                            // 1. Creamos el nuevo objeto
                            val nuevoAlumno = Alumno(
                                nombre = nombre,
                                ciudad = ciudad,
                                edad = edad,
                                curso = "DAM2T"
                            )

                            // 2. Lo añadimos a la lista reactiva
                            listaAlumnos.add(nuevoAlumno)

                            // 3. Limpiamos las cajas de texto
                            nombre = ""
                            ciudad = ""
                            edadTexto = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Alumno")
                    }

                }

                ListaAlumnos(alumnos = listaAlumnos)
            }
        }
    }
}

// --- 8. COMPONENTE PARA MOSTRAR CADA ALUMNO EN UNA TARJETA ---
@Composable
fun AlumnoCard(alumno: Alumno) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = alumno.nombre,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            // --- TEXTO AÑADIDO (PRÁCTICA 3) ---
            Text(
                text = "Alumno de: ${alumno.curso}",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text("Ciudad: ${alumno.ciudad}")
            Text("${alumno.edad} años")

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

// --- 10. MOSTRAR LA LISTA CON LAZYCOLUMN ---
@Composable
fun ListaAlumnos(alumnos: List<Alumno>) {
    LazyColumn {
        items(alumnos) { alumno ->
            AlumnoCard(alumno = alumno)
        }
    }
}

// --- 7. REPRESENTAR UN ALUMNO MEDIANTE UN OBJETO ---
data class Alumno(
    val nombre: String,
    val ciudad: String,
    val edad: Int,
    val curso: String = "SIN ASIGNAR"
)

// --- 9. TRABAJAR CON UNA COLECCIÓN DE ALUMNOS ---
val alumnos = listOf(
    Alumno("Ana", "Ciudad Real", 20),
    Alumno("Luis", "Toledo", 17),
    Alumno("Marta", "Albacete", 19),
    Alumno("Pablo", "Cuenca", 21),
    Alumno("Sara", "Guadalajara", 18)
)