package tr.com.alkimkivanccivi.kizuna

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import tr.com.alkimkivanccivi.kizuna.Response.Loading
import tr.com.alkimkivanccivi.kizuna.Response.Success

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    val oneTapClient: SignInClient
): ViewModel() {

    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase

    var oneTapSignInResponse by mutableStateOf<Response<BeginSignInResult>>(Success(null))
        private set
    var signInWithGoogleResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set


    fun oneTapSignIn() = viewModelScope.launch {
        oneTapSignInResponse = Loading
        oneTapSignInResponse = repo.oneTapSignInWithGoogle()
    }

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        oneTapSignInResponse = Loading
        signInWithGoogleResponse = repo.firebaseSignInWithGoogle(googleCredential)
    }
}