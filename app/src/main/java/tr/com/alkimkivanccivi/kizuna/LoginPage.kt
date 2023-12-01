package tr.com.alkimkivanccivi.kizuna

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import tr.com.alkimkivanccivi.kizuna.Response.Success
import tr.com.alkimkivanccivi.kizuna.Response.Failure
import tr.com.alkimkivanccivi.kizuna.Response.Loading

@Composable
fun LoginPage(navController: NavController, ) {
    val x = LocalContext.current
    val viewModel: AuthViewModel = hiltViewModel()
    if (viewModel.isUserAuthenticated) {
        navController.navigate("contactspage")
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials = Identity.getSignInClient(x).getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }
    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }
    when(val oneTapSignInResponse: Response<BeginSignInResult> = viewModel.oneTapSignInResponse) {
        is Loading -> Text(text = "loading")
        is Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            Log.e("a","",oneTapSignInResponse.e)
        }
    }

    when(val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Loading -> Text(text = "googleloading")
        is Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                Log.d("TAG", "LoginPage: signin start")
                if (signedIn) navController.navigate("contactspage")
                else Log.d("TAG", "LoginPage: signedIn")
                //navigateToHomeScreen(signedIn)

            }
        }
        is Failure -> LaunchedEffect(Unit) {
            Log.d("TAG", "LoginPage: fail")
            print(signInWithGoogleResponse.e)
        }
    }
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
                    viewModel.oneTapSignIn()
                    //navController.navigate("contactspage")

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
