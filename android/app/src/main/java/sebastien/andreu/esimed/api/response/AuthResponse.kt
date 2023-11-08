package sebastien.andreu.esimed.api.response

data class AuthResponse(
    val token: String?,
    val user: String,
    val error: String?
)