package jpf.simple_messenger_android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jpf.simple_messenger_android.domain.User
import jpf.simple_messenger_android.viewmodels.MessengerViewModel

@Composable
fun ChatScreen(
    viewModel: MessengerViewModel,
    user: User,
    friendLogin: String
) {
    var messageText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.startPolling(listOf(user.login, user.oauthToken, friendLogin))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(viewModel.messages) { message ->
                MessageItem(message, isMine = message.startsWith("Me: "))
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Введите сообщение") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        val sendMessageRequest = listOf(
                            user.login,
                            user.oauthToken,
                            friendLogin,
                            messageText
                        )
                        viewModel.sendMessage(sendMessageRequest) {
                            viewModel.messages.add(0, "Me: $messageText")
                            messageText = ""
                        }
                    }
                }
            ) {
                Text("Отправить")
            }
        }
    }
}