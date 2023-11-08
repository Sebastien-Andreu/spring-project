package sebastien.andreu.esimed.api.response

data class CreateTierListResponse(
    val message: String?,
    val list: String,
    val error: String?
)