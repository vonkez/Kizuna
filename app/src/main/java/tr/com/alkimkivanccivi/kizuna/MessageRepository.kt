package tr.com.alkimkivanccivi.kizuna

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Date
import kotlin.math.log

@Serializable
data class NewMessagesResult(val messages: List<Message>, val users: Map<String, String>)

@Serializable
data class Message(val senderId: String, val receiverId: String, val messageContent: String, val createdAt: Long)

enum class ConnectionStatus{
    Connecting,
    Connected,
    NotConnected

}

class MessageRepository {
    val auth = Firebase.auth
    var websocketSession = mutableStateOf<DefaultClientWebSocketSession?>(null)
    var users = mutableStateMapOf<String,String>()
    var messages = mutableStateMapOf<String,List<Message>>()
    var connectionStatus = mutableStateOf(ConnectionStatus.NotConnected)


    suspend fun startListening() {
        connectionStatus.value = ConnectionStatus.Connecting
        if (auth.currentUser == null) return
        val token = auth.currentUser!!.getIdToken(true).await().token ?: return

        val client = createClient(token)
        fetchPreviousMessages(client)

        try {
            client.webSocket(
                method = HttpMethod.Get,
                host = "kizuna-backend.alkimkivanccivi.com.tr",
                port = 80,
                path = "/ws"
            ) {
                try {
                    send("Auth $token")
                    websocketSession.value = this
                    connectionStatus.value = ConnectionStatus.Connected
                    Log.d("TAG", "startListening: connected $websocketSession")
                    /*
                    while (true) {
                        Log.d("TAG", "websocketloop: start $websocketSession")
                        Thread.sleep(3000)
                    }*/
                    while (true) {
                        val othersMessage = incoming.receive() as? Frame.Text ?: continue
                        val msgText = othersMessage.readText()
                        if (msgText.startsWith("MSG")) {
                            val (_, uid, msg) = msgText.split(" ", limit = 3)
                            //val message = Json.decodeFromString<Message>(msg)
                            val message = Message(uid, auth.uid!!, msg, Date().time)
                            var messagesList = messages[uid]
                            if (messagesList == null){ // new contact
                                messagesList = mutableListOf()
                                val displayName = fetchDisplayName(client, uid)
                                users[uid] = displayName
                            }
                            val tempMessageList = messagesList!!.toMutableList()
                            tempMessageList.add(message)
                            messages[uid] = tempMessageList
                            //val b = messageByUser.value[uid]!!
                            //b.add(message)
                        }

                    }
                } catch (e: Exception) {
                    Log.e("TAG", "websocket error ", e)
                }

            }
        } catch (e: Exception) {
            Log.e("TAG", "Can't connect to websocket ", e)

        }
        Log.d("TAG", "startListening: websocket nulled")
        //websocketSession = null
        connectionStatus.value = ConnectionStatus.NotConnected

    }

    suspend fun sendMessage(uid: String, content: String){
        if (this.websocketSession.value == null) {
            Log.d("TAG", "sendMessage: websocket == null")
            return
        }
        Log.d("TAG", "sendMessage: websocket!!!!!")

        websocketSession.value!!.send("MSG $uid $content")

        val message = Message(auth.uid!!, uid, content, Date().time)
        var messagesList = messages[uid]
        if (messagesList == null){ // new contact
            messagesList = mutableListOf()
        }
        val tempMessageList = messagesList!!.toMutableList()
        tempMessageList.add(message)
        messages[uid] = tempMessageList
    }

    fun createClient(token: String): HttpClient {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(WebSockets)
            defaultRequest {
                header("Authorization", token)
            }
        }
        return client
    }

    suspend fun fetchPreviousMessages(client: HttpClient): Unit {
        val result: NewMessagesResult =
            client.get("http://kizuna-backend.alkimkivanccivi.com.tr:80/newMessages/0").body()
        val temp = result.users
        users.putAll(result.users)
        users.put(auth.uid!!, auth.currentUser?.displayName ?: auth.currentUser!!.email!!)
        val tempMessages = mutableMapOf<String, MutableList<Message>>()
        result.messages.forEach {
            val author = if (it.receiverId == auth.currentUser!!.uid) it.senderId else it.receiverId
            tempMessages.putIfAbsent(author, mutableListOf())
            tempMessages[author]!!.add(it)
        }
        val sorted = tempMessages.toSortedMap(compareBy { tempMessages[it]!!.first().createdAt })
        messages.putAll(sorted)
    }

    suspend fun fetchDisplayName(client: HttpClient, uid: String): String {
        return client.get("http://kizuna-backend.alkimkivanccivi.com.tr:80/displayName/$uid").body()
    }



}