package tr.com.alkimkivanccivi.kizuna

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) { AppState(navController) }
class AppState(val navController: NavHostController) {
    fun navigateToContact(): Unit {
        navController.navigate("contactspage")
    }

    fun navigateToChat(): Unit {
        navController.navigate("chatpage")
    }
}