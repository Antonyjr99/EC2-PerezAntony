package pe.idat.ec2_perezantony


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.idat.ec2_perezantony.ui.theme.EC2PerezAntonyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EC2PerezAntonyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "menu") {
                        composable("menu") { MenuScreen(navController) }
                        composable("cuestionario") { CuestionarioScreen(navController) }
                        composable("eventos") { EventosScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menú Principal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = { /* primera opción */ }, modifier = Modifier.padding(8.dp)) {
            Text("Primera opción")
        }
        Button(onClick = { navController.navigate("cuestionario") }, modifier = Modifier.padding(8.dp)) {
            Text("Cuestionario")
        }
        Button(onClick = { navController.navigate("eventos") }, modifier = Modifier.padding(8.dp)) {
            Text("Eventos internacionales")
        }
    }
}

@Composable
fun CuestionarioScreen(navController: NavController) {
    var habilidades by remember { mutableStateOf(setOf<String>()) }
    var significadoTrabajo by remember { mutableStateOf("") }
    var pagaTrabajo by remember { mutableStateOf("") }
    var trabajaPresion by remember { mutableStateOf(false) }
    var oportunidadCrecimiento by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Cuestionario", style = MaterialTheme.typography.titleLarge, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(8.dp))

        Text("1. Marque sus habilidades:", style = MaterialTheme.typography.bodySmall)
        CheckboxList(habilidades, onSelectedItemsChange = { habilidades = it })

        Spacer(modifier = Modifier.height(8.dp))

        Text("2. ¿Cuán significativo es tu trabajo?", style = MaterialTheme.typography.bodySmall)
        RadioButtonGroupOptions(options = listOf("Mucho", "Más o menos", "Poco"), selected = significadoTrabajo, onSelectedChange = { significadoTrabajo = it })

        Spacer(modifier = Modifier.height(8.dp))

        Text("3. ¿Qué tan bien te pagan en el trabajo que haces?", style = MaterialTheme.typography.bodySmall)
        RadioButtonGroupOptions(options = listOf("Bien", "Regular", "Mal"), selected = pagaTrabajo, onSelectedChange = { pagaTrabajo = it })

        Spacer(modifier = Modifier.height(8.dp))

        Text("4. ¿Trabajas bajo presión?", style = MaterialTheme.typography.bodySmall)
        RadioButtonGroupBoolean(selected = trabajaPresion, onSelectedChange = { trabajaPresion = it })

        Spacer(modifier = Modifier.height(8.dp))

        Text("5. ¿Tienes oportunidad de crecimiento en tu trabajo?", style = MaterialTheme.typography.bodySmall)
        RadioButtonGroupBoolean(selected = oportunidadCrecimiento, onSelectedChange = { oportunidadCrecimiento = it })

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    println("Resolviendo cuestionario...")
                    println("Habilidades: $habilidades")
                    println("Significado del trabajo: $significadoTrabajo")
                    println("Paga del trabajo: $pagaTrabajo")
                    println("¿Trabajas bajo presión?: $trabajaPresion")
                    println("¿Oportunidad de crecimiento?: $oportunidadCrecimiento")
                    navController.popBackStack() // Regresa a la ventana anterior
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("RESOLVER")
            }

            Button(
                onClick = { navController.popBackStack() }, // Acción para regresar
                modifier = Modifier.padding(4.dp)
            ) {
                Text("REGRESAR")
            }
        }
    }
}


@Composable
fun CheckboxList(selectedItems: Set<String>, onSelectedItemsChange: (Set<String>) -> Unit) {
    val skills = listOf(
        "Autoconocimiento",
        "Empatía",
        "Comunicación asertiva",
        "Toma de decisiones",
        "Pensamiento crítico",
        "Ninguno"
    )

    Column {
        skills.forEach { skill ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedItems.contains(skill),
                    onCheckedChange = {
                        val newSelectedItems = if (it) {
                            selectedItems + skill
                        } else {
                            selectedItems - skill
                        }
                        onSelectedItemsChange(newSelectedItems)
                    }
                )
                Text(skill, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun RadioButtonGroupOptions(options: List<String>, selected: String, onSelectedChange: (String) -> Unit) {
    Column {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selected == option,
                    onClick = { onSelectedChange(option) }
                )
                Text(option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun RadioButtonGroupBoolean(selected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    Row {
        RadioButton(
            selected = selected,
            onClick = { onSelectedChange(true) },
            modifier = Modifier.padding(end = 8.dp)
        )
        Text("SI")

        RadioButton(
            selected = !selected,
            onClick = { onSelectedChange(false) },
            modifier = Modifier.padding(start = 16.dp)
        )
        Text("NO")
    }
}

data class Evento(val titulo: String, val descripcion: String, val fecha: String)

@Composable
fun EventosScreen() {
    val eventos = listOf(
        Evento("Evento 1", "Descripción del evento 1", "01/01/2024"),
        Evento("Evento 2", "Descripción del evento 2", "02/01/2024"),
        Evento("Evento 3", "Descripción del evento 3", "03/01/2024"),
        Evento("Evento 4", "Descripción del evento 4", "04/01/2024"),
        Evento("Evento 5", "Descripción del evento 5", "05/01/2024"),
        Evento("Evento 6", "Descripción del evento 6", "06/01/2024"),
        Evento("Evento 7", "Descripción del evento 7", "07/01/2024"),
        Evento("Evento 8", "Descripción del evento 8", "08/01/2024"),
        Evento("Evento 9", "Descripción del evento 9", "09/01/2024"),
        Evento("Evento 10", "Descripción del evento 10", "10/01/2024"),
        Evento("Evento 11", "Descripción del evento 11", "11/01/2024"),
        Evento("Evento 12", "Descripción del evento 12", "12/01/2024")
    )

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(eventos.size) { index ->
            EventoCard(eventos[index])
        }
    }
}

@Composable
fun EventoCard(evento: Evento) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = evento.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = evento.descripcion, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = evento.fecha, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EC2PerezAntonyTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                CuestionarioScreen(rememberNavController())
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle action */ },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("REGRESAR")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


