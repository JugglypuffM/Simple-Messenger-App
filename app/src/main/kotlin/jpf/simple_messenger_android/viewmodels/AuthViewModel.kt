package jpf.simple_messenger_android.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jpf.simple_messenger_android.domain.User
import jpf.simple_messenger_android.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    fun startAuthFlow(context: Context) {
        viewModelScope.launch {
            try {
                val authUrl = repository.getAuthUrl()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
                context.startActivity(intent)
            } catch (e: Exception) {
                // Обработка ошибок
                Log.e("AuthViewModel", "Ошибка при запуске авторизации", e)
            }
        }
    }

    fun submitAuthCode(code: String, onSuccess: (User) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.submitAuthCode(code)
                onSuccess(user)
            } catch (e: Exception) {
                // Обработка ошибок
                Log.e("AuthViewModel", "Ошибка при отправке кода авторизации", e)
            }
        }
    }
}
