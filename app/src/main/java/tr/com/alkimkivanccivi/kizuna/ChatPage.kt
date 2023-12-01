@file:OptIn(ExperimentalMaterial3Api::class)

package tr.com.alkimkivanccivi.kizuna

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import tr.com.alkimkivanccivi.kizuna.ui.theme.KizunaTheme


@ExperimentalMaterial3Api
@Composable
fun ChatPage(navController: NavController, uid: String, displayName: String) {
    var textBoxValue by remember { mutableStateOf("") }
    val viewModel: ChatViewModel = hiltViewModel()
    var messages = viewModel.messageRepository.messages[uid] ?: listOf()
    val scrollState = rememberScrollState(100)
    LaunchedEffect(key1 = messages){
        scrollState.animateScrollTo(999999)
    }



    return Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(displayName)
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

                },

                )
        },

        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(bottom = 70.dp)
                .verticalScroll(scrollState)
                .fillMaxWidth()
        ) {

            messages.forEachIndexed { index, message ->
                val isMessageMine = message.receiverId == uid

                ChatMessage(
                    message = message.messageContent,
                    author = if (isMessageMine) "You" else displayName,
                    myMessage = isMessageMine,
                    firstMessage = messages.getOrNull(index - 1)?.senderId != message.senderId
                )
            }
            /*
            ChatMessage(
                message = "Test test testTTTEee tetet ",
                author = "Ali veli",
                myMessage = false,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet 2",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet 3",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet  jfgfgj",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet asd ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet gdfg ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet ",
                author = "Ali veli",
                myMessage = false,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet 2",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee dgdfg 3",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet  jfgfgj",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet asd ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet gdfg ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet ",
                author = "Ali veli",
                myMessage = false,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet 2",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee dgdfg 3",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet  jfgfgj",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet asd ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet gdfg ",
                author = "Kıvanç",
                myMessage = true,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee tetet ",
                author = "Ali veli",
                myMessage = false,
                firstMessage = true
            )
            ChatMessage(
                message = "Test test testTTTEee tetet 2",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )
            ChatMessage(
                message = "Test test testTTTEee dgdfg 3",
                author = "Ali veli",
                myMessage = false,
                firstMessage = false
            )

             */

        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = textBoxValue,
                    onValueChange = { s -> textBoxValue = s },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier.padding(8.dp)
                )
                IconButton(onClick = {
                    print(messages)
                    viewModel.sendMessage(uid, textBoxValue)
                    textBoxValue = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                            .size(35.dp)
                    )
                }
            }
        }


    }

}

@Composable
fun ChatMessage(message: String, author: String, myMessage: Boolean, firstMessage: Boolean) {
    val hArrangement = if (myMessage) Arrangement.End else Arrangement.Start
    val hAlignment = if (myMessage) Alignment.End else Alignment.Start
    val shapeTopStart = if (myMessage) 25 else 0
    val shapeTopEnd = if (myMessage) 0 else 25
    val color =
        if (myMessage) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = hArrangement,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!myMessage) {
            if (firstMessage) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Contact",
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .border(
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            CircleShape
                        )
                        .size(30.dp)
                        .padding(4.dp)
                )
            } else {
                Box(modifier = Modifier.size(42.dp, 1.dp))
            }
        }

        Column(horizontalAlignment = hAlignment, modifier = Modifier.padding(bottom = 6.dp)) {
            if (firstMessage) Text(author)
            ElevatedCard(
                shape = RoundedCornerShape(shapeTopStart, shapeTopEnd, 25, 25),
                colors = CardDefaults.elevatedCardColors(containerColor = color),

                ) {
                Text(text = message, modifier = Modifier.padding(6.dp))
            }
        }
        if (myMessage) {
            if (firstMessage) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Contact",
                    modifier = Modifier
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .border(
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            CircleShape
                        )
                        .size(30.dp)
                        .padding(4.dp)
                )
            } else {
                Box(modifier = Modifier.size(42.dp))
            }
        }
    }
}
