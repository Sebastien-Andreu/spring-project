package sebastien.andreu.esimed.api.response

data class CreateRankResponse(
    val message: String?,
    val rank: String,
    val error: String?
)