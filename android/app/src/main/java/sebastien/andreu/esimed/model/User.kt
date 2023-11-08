package sebastien.andreu.esimed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "userId")
    val userId: Long? = null,

    @field:Json(name = "email")
    val email: String,

    @field:Json(name = "pseudo")
    val pseudo: String,

    @field:Json(name = "password")
    val password: String = "",

    @field:Json(name = "role")
    val role: String? = "USER"
) {

    fun allDataIsValid(): Boolean {
        return with (this) {
            listOf(email, password, pseudo ).all { it.isNotBlank() }
        }
    }

    fun getBody(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["email"] = email
        mutableMap["pseudo"] = pseudo
        mutableMap["password"] = password
        return mutableMap
    }
}
