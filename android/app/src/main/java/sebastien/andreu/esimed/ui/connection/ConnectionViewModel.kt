package sebastien.andreu.esimed.ui.connection

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
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.AuthResponse
import sebastien.andreu.esimed.extension.toMD5
import sebastien.andreu.esimed.model.User
import sebastien.andreu.esimed.utils.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "ConnectionActivityViewModel"

    val apiResponse: MutableLiveData<StatusApi<AuthResponse>> = MutableLiveData()

    fun connect(context: Context, email: String, password: String) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(2, TimeUnit.SECONDS)
            apiInjector.login(getBody(email, password)).let {
                if (it.isSuccessful) {
                    apiResponse.postValue(it.body()!!.token?.let { it1 -> StatusApi.success(it1, it.body()) })
                    StoreUser.user = Gson().fromJson(it.body()!!.user, User::class.java)
                    Log.e("----", "${StoreUser.user}")
                } else {
                    Gson().fromJson(it.errorBody()!!.charStream(), AuthResponse::class.java)?.let { errorResponse ->
                        apiResponse.postValue(errorResponse.error?.let { it1 -> StatusApi.error(it1, null) })
                    }
                }
            }
        } catch (e: UnknownHostException) {
            apiResponse.postValue(StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null))
        } catch (e: SocketTimeoutException) {
            apiResponse.postValue(StatusApi.error(context.getString(R.string.SocketTimeoutException), null))
        } catch (e: ConnectException) {
            apiResponse.postValue(StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null))
        } catch (e: Exception) {
            apiResponse.postValue(StatusApi.error("Impossible de se connecter", null))
        }
    }

    private fun getBody(login: String, password: String): MutableMap<String, Any?> {
        val mutableMap: MutableMap<String, Any?> = mutableMapOf()
        mutableMap[PSEUDO] = login
        mutableMap[PASSWORD] = password
        return mutableMap
    }
}