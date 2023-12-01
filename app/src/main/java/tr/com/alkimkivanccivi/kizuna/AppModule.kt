package tr.com.alkimkivanccivi.kizuna

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)


    @Provides
    @Named("SIGN_UP_REQUEST")
    fun provideSignUpRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("414687229694-q961v3qep1sjkjfma19vp4cq2sofqsgg.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    @Named("SIGN_IN_REQUEST")
    fun provideSignInRequest() =
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("414687229694-q961v3qep1sjkjfma19vp4cq2sofqsgg.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

    @Provides
    fun provideAuthRepository(
        oneTapClient: SignInClient,
        @Named("SIGN_IN_REQUEST")
        signInRequest: BeginSignInRequest,
        @Named("SIGN_UP_REQUEST")
        signUpRequest: BeginSignInRequest,
    ): AuthRepository = AuthRepository(
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
    )

    @Provides
    fun provideContactsViewModel(authRepository: AuthRepository, messageRepository: MessageRepository) = ContactsViewModel(authRepository, messageRepository)

    @Provides
    fun provideChatViewModel(contactsViewModel: ContactsViewModel, messageRepository: MessageRepository) = ChatViewModel(contactsViewModel, messageRepository)


    @Singleton
    @Provides
    fun provideMessageRepository() = MessageRepository()
}