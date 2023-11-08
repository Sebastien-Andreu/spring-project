package sebastien.andreu.esimed.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.*
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.ApiInjector
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.AuthResponse
import sebastien.andreu.esimed.extension.toMD5
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.utils.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "HomeViewModel"

    val apiResponse: MutableLiveData<StatusApi<AuthResponse>> = MutableLiveData()
    val getListOfTierList: MutableLiveData<Triple<Status, String?, ArrayList<TierList>?>> = MutableLiveData()

    var listOfTierList: ArrayList<TierList> = arrayListOf()

    fun getListOfTierList(context: Context) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.getAllTierList(getHeader(context), StoreUser.user?.userId ?: 0).let {
                if (it.isSuccessful) {
                    (it.body()!! as ArrayList<TierList>).let { tierListlist ->
                        Log.e("----", "$tierListlist")
                        getListOfTierList.postValue(Triple(Status.SUCCESS,null, tierListlist))
                        listOfTierList = tierListlist
                    }
                } else {
                    getListOfTierList.postValue(Triple(Status.ERROR, it.message(), arrayListOf()))
                }
            }
        } catch (e: UnknownHostException) {
            getListOfTierList.postValue(Triple(Status.ERROR, context.getString(R.string.UnknownHostException, IP, PORT), null))
        } catch (e: SocketTimeoutException) {
            getListOfTierList.postValue(Triple(Status.ERROR, context.getString(R.string.SocketTimeoutException), null))
        } catch (e: ConnectException) {
            getListOfTierList.postValue(Triple(Status.ERROR, context.getString(R.string.UnknownHostException, IP, PORT), null))
        }
    }

    private fun getBody(login: String, password: String): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap[PSEUDO] = login
        mutableMap[PASSWORD] = password
        return mutableMap
    }

    private fun getBodyUserId(): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap["userId"] = StoreUser.user?.userId
        return mutableMap
    }


    private fun getHeader(context: Context): Map<String, String> {
        val mutableMap = HashMap<String, String>()
        mutableMap[TOKEN] = context.getString(R.string.send_token, Token.value.toString())
        return mutableMap
    }
}