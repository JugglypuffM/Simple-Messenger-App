package jpf.simple_messenger_android.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val name: String,
    val oauthToken: String
)