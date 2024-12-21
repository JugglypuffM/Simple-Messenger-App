package jpf.simple_messenger_android.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import jpf.simple_messenger_android.domain.User
import jpf.simple_messenger_android.serialization.Serializer
import jpf.simple_messenger_android.viewmodels.MessengerViewModel

@Composable
fun MainScreen(
    user: User,
    viewModel: MessengerViewModel,
    onChatStart: (String) -> Unit,
    onExit: () -> Unit
) {
    val context: Context = LocalContext.current
    var friendLogin by remember { mutableStateOf("") }
    var isDeleteRequested by remember { mutableStateOf(false) }

    LaunchedEffect(isDeleteRequested) {
        if (isDeleteRequested) {
            Serializer.deleteUserData(context)
            isDeleteRequested = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = friendLogin,
            onValueChange = { friendLogin = it },
            placeholder = { Text("Введите логин друга") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val request = listOf(user.login, user.oauthToken, friendLogin)
            viewModel.findFriend(
                request,
                onSuccess = { onChatStart(friendLogin) }
            )
        }) {
            Text("Найти")
        }
        Button(onClick = {
            onExit()
        }) {
            Text("Выйти")
        }
    }
}