package sebastien.andreu.esimed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sebastien.andreu.esimed.extension.toMD5
import sebastien.andreu.esimed.utils.ADDRESS
import sebastien.andreu.esimed.utils.BIRTHDAY
import sebastien.andreu.esimed.utils.CAMAX
import sebastien.andreu.esimed.utils.CONTACT
import sebastien.andreu.esimed.utils.EMAIL
import sebastien.andreu.esimed.utils.ID
import sebastien.andreu.esimed.utils.ID_CLIENT
import sebastien.andreu.esimed.utils.NAME
import sebastien.andreu.esimed.utils.PASSWORD
import sebastien.andreu.esimed.utils.STATUS
import sebastien.andreu.esimed.utils.SURNAME
import sebastien.andreu.esimed.utils.TAUXCHARGE
import sebastien.andreu.esimed.utils.TEL

@JsonClass(generateAdapter = true)
data class Project(
    @field:Json(name = ID)
    val id: Long? = null,

    @field:Json(name = ID_CLIENT)
    val idClient: Long,

    @field:Json(name = NAME)
    val name: String,

    @field:Json(name = STATUS)
    val status: String

) {

    fun allDataIsValid(): Boolean {
        return with (this) {
            listOf(name, status).all { it.isNotBlank() }
        }
    }

    fun getBody(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap[ID_CLIENT] = idClient
        mutableMap[NAME] = name
        mutableMap[STATUS] = status
        return mutableMap
    }

    fun getBodyWithId(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap[ID] = id
        mutableMap[ID_CLIENT] = idClient
        mutableMap[NAME] = name
        mutableMap[STATUS] = status
        return mutableMap
    }
}
