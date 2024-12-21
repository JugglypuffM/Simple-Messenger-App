package jpf.simple_messenger_android.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import jpf.simple_messenger_android.domain.User
import jpf.simple_messenger_android.viewmodels.AuthViewModel

@Composable
fun AuthScreen(onAuthSuccess: (User) -> Unit, viewModel: AuthViewModel) {
    val context = LocalContext.current
    var authCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.startAuthFlow(context) }) {
            Text("Авторизоваться через Яндекс")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = authCode,
            onValueChange = { authCode = it },
            placeholder = { Text("Введите код авторизации") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.submitAuthCode(authCode, onAuthSuccess)
        }) {
            Text("Войти")
        }
    }
}