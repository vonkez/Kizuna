package tr.com.alkimkivanccivi.kizuna

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val contactsViewModel: ContactsViewModel ,
    val messageRepository: MessageRepository
): ViewModel() {
    var uid = mutableStateOf("")
    var messages = mutableStateOf(listOf<Message>())

    fun sendMessage(uid: String, content: String){
        GlobalScope.launch {
            Log.d("TAG", "send start chat: $content")
            messageRepository.sendMessage(uid, content)
        }
    }
}