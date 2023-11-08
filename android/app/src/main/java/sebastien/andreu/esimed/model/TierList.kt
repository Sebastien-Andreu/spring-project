package sebastien.andreu.esimed.model

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sebastien.andreu.esimed.utils.StoreUser
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@JsonClass(generateAdapter = true)
data class TierList(
    @field:Json(name = "listId")
    val listId: Long? = null,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "tag")
    val tag: String,

    @field:Json(name = "updateTime")
    val updateTime: String? = null,

    @field:Json(name = "userId")
    val userId: Long? = null
) {

    fun allDataIsValid(): Boolean {
        return with (this) {
            listOf(title, tag).all { it.isNotBlank() }
        }
    }

    fun getBodyCreate(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["title"] = title
        mutableMap["tag"] = tag
        mutableMap["userId"] = StoreUser.user?.userId
        return mutableMap
    }

    fun getBodyUpdate(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["listId"] = listId
        mutableMap["title"] = title
        mutableMap["tag"] = tag
        mutableMap["updateTime"] = updateTime
        mutableMap["userId"] = StoreUser.user?.userId
        Log.e("----", "$mutableMap")
        return mutableMap
    }
}
