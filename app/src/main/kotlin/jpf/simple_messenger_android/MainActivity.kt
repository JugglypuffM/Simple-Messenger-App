package jpf.simple_messenger_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import jpf.simple_messenger_android.view.AppNavigation
import jpf.simple_messenger_android.serialization.Serializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = Serializer.getUserData(this)
        val startDestination = if (user.isEmpty) "auth" else "main"
        setContent {
            AppNavigation(this, startDestination)
        }
    }
}