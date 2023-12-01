package tr.com.alkimkivanccivi.kizuna

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import tr.com.alkimkivanccivi.kizuna.Response.Success
import tr.com.alkimkivanccivi.kizuna.Response.Failure

typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<Boolean>

@Singleton
class AuthRepository @Inject constructor(
    private var oneTapClient: SignInClient,
    @Named("SIGN_IN_REQUEST")
    private var signInRequest: BeginSignInRequest,
    @Named("SIGN_UP_REQUEST")
    private var signUpRequest: BeginSignInRequest,
) {
    val auth = Firebase.auth
    val isUserAuthenticatedInFirebase = auth.currentUser != null

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            // val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


}

