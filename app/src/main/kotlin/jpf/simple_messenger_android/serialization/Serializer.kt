package jpf.simple_messenger_android.serialization

import android.content.Context
import jpf.simple_messenger_android.domain.User
import java.util.Optional

object Serializer {
    fun saveUserData(context: Context, user: User) {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("login", user.login)
            putString("name", user.name)
            putString("oauthToken", user.oauthToken)
            apply()
        }
    }

    fun getUserData(context: Context): Optional<User> {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val login = sharedPref.getString("login", null) ?: return Optional.empty()
        val name = sharedPref.getString("name", null) ?: return Optional.empty()
        val oauthToken = sharedPref.getString("oauthToken", null) ?: return Optional.empty()
        return Optional.of(User(login, name, oauthToken))
    }

    fun deleteUserData(context: Context) {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}