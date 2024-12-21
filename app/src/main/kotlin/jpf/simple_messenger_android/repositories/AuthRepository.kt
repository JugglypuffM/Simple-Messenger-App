package jpf.simple_messenger_android.repositories

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import jpf.simple_messenger_android.domain.User

class AuthRepository(private val baseUrl: String, private val client: HttpClient) {
    suspend fun getAuthUrl(): String {
        val response: HttpResponse = client.get("$baseUrl/login")
        return response.call.request.url.toString()
    }

    suspend fun submitAuthCode(code: String): User {
        val response: HttpResponse = client.post("$baseUrl/login") {
            setBody(code)
        }
        return response.body() // предполагается, что возвращается токен в виде строки
    }
}