package sebastien.andreu.esimed.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sebastien.andreu.esimed.extension.toMD5
import sebastien.andreu.esimed.utils.ADDRESS
import sebastien.andreu.esimed.utils.BIRTHDAY
import sebastien.andreu.esimed.utils.CAMAX
import sebastien.andreu.esimed.utils.EMAIL
import sebastien.andreu.esimed.utils.ID
import sebastien.andreu.esimed.utils.NAME
import sebastien.andreu.esimed.utils.PASSWORD
import sebastien.andreu.esimed.utils.ROLE
import sebastien.andreu.esimed.utils.SURNAME
import sebastien.andreu.esimed.utils.TAUXCHARGE
import sebastien.andreu.esimed.utils.TEL

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = ID)
    val id: Long? = null,

    @field:Json(name = NAME)
    val name: String,

    @field:Json(name = SURNAME)
    val surname: String,

    @field:Json(name = ROLE)
    val role: Long? = 1,

    @field:Json(name = BIRTHDAY)
    val birthday: String,

    @field:Json(name = ADDRESS)
    val address: String,

    @field:Json(name = EMAIL)
    val email: String,

    @field:Json(name = TEL)
    val tel: String,

    @field:Json(name = CAMAX)
    val caMax: String,

    @field:Json(name = TAUXCHARGE)
    val tauxCharge: String,

    @field:Json(name = PASSWORD)
    val password: String
) {

    fun allDataIsValid(): Boolean {
        return with (this) {
            listOf(name, surname, role.toString(), birthday, address, email, tel, CAMAX, tauxCharge, password).all { it.isNotBlank() }
        }
    }

    fun getBody(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap[NAME] = name
        mutableMap[SURNAME] = surname
        mutableMap[BIRTHDAY] = birthday
        mutableMap[ADDRESS] = address
        mutableMap[EMAIL] = email
        mutableMap[TEL] = tel
        mutableMap[CAMAX] = caMax
        mutableMap[TAUXCHARGE] = tauxCharge
        mutableMap[PASSWORD] = password.toMD5()
        return mutableMap
    }
}
