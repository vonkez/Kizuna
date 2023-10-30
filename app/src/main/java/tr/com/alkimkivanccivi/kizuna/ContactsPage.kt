@file:OptIn(ExperimentalMaterial3Api::class)

package tr.com.alkimkivanccivi.kizuna

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactsPage(navController: NavController) {
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
                Surface(onClick = { navController.navigate("chatpage") }) {
                    ContactRow()
                }

            }
        }


    }

}

@Composable
fun ContactRow(): Unit {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Contact",
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 9.dp)
                .border(border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary), CircleShape)
                .size(50.dp)
                .padding(6.dp)


        )
        Column(Modifier.weight(1f)) {
            Text(
                text = "Ali Veli  dsfsdfklkl dsfsdfklkl dsfsdfklkl dsfsdfklkl ",
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = "naber 3453 3453",
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.W400,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }


        Text(
            text = "01/01/2020",
            Modifier
                .padding(6.dp)
                .align(Alignment.Top),
            softWrap = false,
            maxLines = 1
        )
    }
}