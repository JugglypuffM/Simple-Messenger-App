package jpf.simple_messenger_android.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jpf.simple_messenger_android.repositories.MessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MessengerViewModel(private val repository: MessageRepository) : ViewModel() {
    var messages = mutableStateListOf<String>()

    fun findFriend(request: List<String>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                messages.clear()
                messages.addAll(repository.findFriend(request))
                onSuccess()
            } catch (e: Exception) {
                Log.e("MessengerViewModel", "Не удалось запросить сообщения", e)
            }
        }
    }

    fun sendMessage(request: List<String>, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val success = repository.sendMessage(request)
                if (success) onSuccess() else Log.e("MessengerViewModel", "Не удалось отправить сообщение")
            } catch (e: Exception) {
                Log.e("MessengerViewModel", "Не удалось отправить сообщение", e)
            }
        }
    }

    fun startPolling(request: List<String>) {
        viewModelScope.launch {
            while (true) {
                try {
                    val newMessages = repository.pollMessages(request)
                    if (newMessages.isNotEmpty()) {
                        messages.addAll(0, newMessages.reversed())
                    }
                } catch (e: Exception) {
                    Log.e("MessengerViewModel", "Не удалось запросить сообщения", e)
                }
                delay(2000)
            }
        }
    }
}