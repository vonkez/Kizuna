package tr.com.alkimkivanccivi.kizuna

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json

import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class ContactsViewModel @Inject constructor(val authRepository: AuthRepository, val messageRepository: MessageRepository): ViewModel() {
    val auth = Firebase.auth
    var messageByUser = messageRepository.messages
    var users = messageRepository.users


    fun startListening(){
        if (messageRepository.connectionStatus.value == ConnectionStatus.NotConnected){
            GlobalScope.launch(Dispatchers.IO) {
                Log.d("TAG", "startListening: GlobalScope launch")
                Log.d("TAG", "startListening: $messageRepository")
                messageRepository.startListening()
            }
        }

    }
    fun sendMessage(uid: String, content: String){
        GlobalScope.launch(Dispatchers.IO) {
            messageRepository.sendMessage(uid, content)

        }
    }



}
