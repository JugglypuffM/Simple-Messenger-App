package jpf.simple_messenger_android.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageRepository(private val baseUrl: String, private val client: HttpClient) {
    suspend fun findFriend(request: List<String>): List<String> {
        val response = client.get("$baseUrl/messenger") {
            setBody(Json.encodeToString(request))
        }
        return response.body()
    }

    suspend fun sendMessage(request: List<String>): Boolean {
        val response: HttpResponse = client.post("$baseUrl/messenger") {
            setBody(Json.encodeToString(request))
        }
        return response.status == HttpStatusCode.OK
    }

    suspend fun pollMessages(request: List<String>): List<String> {
        return client.get("$baseUrl/messenger") {
            setBody(Json.encodeToString(request))
        }.body()
    }
}