package jpf.simple_messenger_android.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import jpf.simple_messenger_android.domain.User
import jpf.simple_messenger_android.repositories.AuthRepository
import jpf.simple_messenger_android.repositories.MessageRepository
import jpf.simple_messenger_android.serialization.Serializer
import jpf.simple_messenger_android.viewmodels.AuthViewModel
import jpf.simple_messenger_android.viewmodels.MessengerViewModel

@Composable
fun AppNavigation(context: Context, startDestination: String) {
    val navController = rememberNavController()
    val httpClient = HttpClient(CIO) {
        install(
            ContentNegotiation
        ) { json() }
    }
    val baseUrl = "http://192.168.1.74:8081"
    val authViewModel = AuthViewModel(AuthRepository(baseUrl, httpClient))
    val messengerViewModel = MessengerViewModel(MessageRepository(baseUrl, httpClient))

    NavHost(navController, startDestination = startDestination) {
        composable("auth") {
            AuthScreen(
                onAuthSuccess = { token ->
                    Serializer.saveUserData(context, token)
                    navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                },
                viewModel = authViewModel
            )
        }
        composable("main") {
            val user = Serializer.getUserData(context).orElse(User("None", "None", "None"))
            MainScreen(user, messengerViewModel,
                { friendLogin ->
                    navController.navigate("chat/${friendLogin}") {
                        popUpTo("home") { inclusive = true }
                    }
                }, {
                    Serializer.deleteUserData(context)
                    navController.navigate("auth")
                }
            )

        }
        composable("chat/{friendLogin}") { backStackEntry ->
            val user = Serializer.getUserData(context).orElse(User("None", "None", "None"))
            val friendLogin =
                backStackEntry.arguments?.getString("friendLogin") ?: return@composable
            ChatScreen(messengerViewModel, user, friendLogin)
        }
    }
}
