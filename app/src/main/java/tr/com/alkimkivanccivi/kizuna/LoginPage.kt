package tr.com.alkimkivanccivi.kizuna

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun LoginPage(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Image(
                painter = painterResource(R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.width(170.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.Start),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(4.dp, 10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(),
                onClick = {
                    navController.navigate("contactspage")
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.google_g_icon),
                        contentDescription = "Google icon",
                        modifier = Modifier
                            .width(54.dp)
                            .align(Alignment.CenterStart)
                    )
                    Text(
                        text = "Continue with Google",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Right,
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    )
                }
            }

            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.Start),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(4.dp, 10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(),
                onClick = { /*TODO*/ }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.github_mark_white),
                        contentDescription = "Github icon",
                        modifier = Modifier
                            .width(54.dp)
                            .align(Alignment.CenterStart)
                    )
                    Text(
                        text = "Continue with Github",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Right,
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    )
                }

            }
        }
    }
}
