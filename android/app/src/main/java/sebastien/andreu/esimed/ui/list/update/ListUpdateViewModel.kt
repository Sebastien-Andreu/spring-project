package sebastien.andreu.esimed.ui.list.update

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.ApiInjector
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.UpdateTierListResponse
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.utils.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ListUpdateViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "ListAddViewModel"

    val apiResponse: MutableLiveData<StatusApi<UpdateTierListResponse>> = MutableLiveData()

    fun updateTierList(context: Context, tierList: TierList) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.updateTierList(getHeader(context), tierList.getBodyUpdate()).let {
                if (it.isSuccessful) {
                    apiResponse.postValue(it.body()!!.message?.let { it1 -> StatusApi.success(it1, it.body()) })
                } else {
                    Gson().fromJson(it.errorBody()!!.charStream(), UpdateTierListResponse::class.java)?.let { errorResponse ->
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
        }
    }

    private fun getHeader(context: Context): Map<String, String> {
        val mutableMap = HashMap<String, String>()
        mutableMap[TOKEN] = context.getString(R.string.send_token, Token.value.toString())
        return mutableMap
    }
}