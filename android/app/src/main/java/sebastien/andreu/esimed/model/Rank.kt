package sebastien.andreu.esimed.model

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sebastien.andreu.esimed.utils.StoreUser
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@JsonClass(generateAdapter = true)
data class Rank(
    @field:Json(name = "rankId")
    val rankId: Long? = null,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "listId")
    val listId: Long,

    @field:Json(name = "ordering")
    val ordering: Int,
) {

    fun allDataIsValid(): Boolean {
        return with (this) {
            listOf(title, ordering.toString()).all { it.isNotBlank() }
        }
    }

    fun getBodyCreate(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["title"] = title
        mutableMap["listId"] = listId
        mutableMap["ordering"] = ordering
        return mutableMap
    }

    fun getBodyUpdate(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["rankId"] = rankId
        mutableMap["title"] = title
        mutableMap["listId"] = listId
        mutableMap["ordering"] = ordering
        return mutableMap
    }
}
