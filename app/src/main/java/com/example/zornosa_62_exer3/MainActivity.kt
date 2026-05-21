package com.example.zornosa_62_exer3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zornosa_62_exer3.ui.AddEventScreen
import com.example.zornosa_62_exer3.ui.EventListScreen
import com.example.zornosa_62_exer3.ui.EventViewModel
import com.example.zornosa_62_exer3.ui.theme.Zornosa_62_Exer3Theme

class MainActivity : ComponentActivity() {
    private val viewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Zornosa_62_Exer3Theme {
                EventApp(viewModel)
            }
        }
    }
}

@Composable
fun EventApp(viewModel: EventViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "event_list") {
        composable("event_list") {
            EventListScreen(
                viewModel = viewModel,
                onAddEventClick = { navController.navigate("add_event") }
            )
        }
        composable("add_event") {
            AddEventScreen(
                onEventAdded = { title, description, date ->
                    viewModel.addEvent(title, description, date)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
