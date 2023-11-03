@file:OptIn(ExperimentalMaterial3Api::class)

package tr.com.alkimkivanccivi.kizuna

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import tr.com.alkimkivanccivi.kizuna.ui.theme.KizunaTheme


@Composable
fun ChatPage(navController: NavController) {
    return Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Kizuna")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back button",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.PersonAdd,
                            contentDescription = "Add Contact",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },

                )
        },

        ) {
        Column(modifier = Modifier.padding(it)) {
            for (x in 1..5) {
                Surface(onClick = { /*TODO*/ }) {
                    ChatMessage()
                }

            }
        }


    }

}

@Composable
fun ChatMessage() {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Contact",
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .border(border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary), CircleShape)
                .size(30.dp)
                .padding(4.dp)
        )
        Column {
            Text("Ali Veli")
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primary),

            ) {
                Text(text = "MEssage mesaageeggege", modifier = Modifier.padding(6.dp))
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ChatPagePreview() {
    KizunaTheme {
        ChatPage(rememberNavController())
    }
}