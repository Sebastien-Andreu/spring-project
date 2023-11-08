package sebastien.andreu.esimed.api.response

import sebastien.andreu.esimed.model.User

data class RegisterResponse(
    val message: String?,
    val user: String,
    val error: String?
)