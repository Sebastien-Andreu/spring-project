package sebastien.andreu.esimed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PictureShow(
    @field:Json(name = "pictureId")
    val pictureId: Long? = null,

    @field:Json(name = "url")
    val url: String,

    @field:Json(name = "tag")
    val tag: String,

    @field:Json(name = "userId")
    val userId: Long,

    @field:Json(name = "available")
    val available: Boolean,
)
