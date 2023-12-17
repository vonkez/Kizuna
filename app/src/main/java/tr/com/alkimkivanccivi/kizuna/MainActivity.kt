@file:OptIn(ExperimentalMaterial3Api::class)

package tr.com.alkimkivanccivi.kizuna

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import tr.com.alkimkivanccivi.kizuna.ui.theme.KizunaTheme

@HiltAndroidApp
class KizunaApp: Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@Composable
fun App() {
    val appState: AppState = rememberAppState()

    KizunaTheme {
        NavHost(navController = appState.navController, startDestination = "login") {
            composable("login") { LoginPage(appState.navController) }
            composable("contactspage") { ContactsPage(appState.navController) }
            composable("chatpage/{uid}/{displayName}" ) { backStackEntry ->
                ChatPage(appState.navController, backStackEntry.arguments!!.getString("uid")!!, backStackEntry.arguments!!.getString("displayName")!!)
            }
        }
    }
}
