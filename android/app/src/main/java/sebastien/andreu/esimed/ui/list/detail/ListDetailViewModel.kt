package sebastien.andreu.esimed.ui.list.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.ApiInjector
import sebastien.andreu.esimed.api.Status
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.CreateTierListResponse
import sebastien.andreu.esimed.model.PictureShow
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.model.User
import sebastien.andreu.esimed.utils.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ListDetailViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "ListAddViewModel"

    val apiResponse: MutableLiveData<StatusApi<CreateTierListResponse>> = MutableLiveData()
    val getPictureByRank: MutableLiveData<StatusApi<List<PictureShow>>> = MutableLiveData()
    val getListOfRank: MutableLiveData<Triple<Status, String?, ArrayList<Rank>?>> = MutableLiveData()

    var listOfTierRank: ArrayList<Rank> = arrayListOf()
    fun getListOfRank(context: Context, list: TierList) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.getRankByListId(getHeader(context), list.listId!!).let {
                if (it.isSuccessful) {
                    (it.body()!! as ArrayList<Rank>).let { rankList ->
                        getListOfRank.postValue(Triple(Status.SUCCESS,null, rankList))
                        listOfTierRank = rankList
                    }
                } else {
                    getListOfRank.postValue(Triple(Status.ERROR, it.message(), arrayListOf()))
                }
            }
        } catch (e: UnknownHostException) {
            getListOfRank.postValue(Triple(Status.ERROR, context.getString(R.string.UnknownHostException, IP, PORT), null))
        } catch (e: SocketTimeoutException) {
            getListOfRank.postValue(Triple(Status.ERROR, context.getString(R.string.SocketTimeoutException), null))
        } catch (e: ConnectException) {
            getListOfRank.postValue(Triple(Status.ERROR, context.getString(R.string.UnknownHostException, IP, PORT), null))
        }
    }

    suspend fun getPictureByRank(context: Context, rankId: Long): StatusApi<List<PictureShow>> {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.getPictureByRank(getHeader(context), rankId).let {
                if (it.isSuccessful) {
                    return StatusApi.success("", it.body())
                } else {
                    return StatusApi.error("Impossible de charger les images", it.body())
                }
            }
        } catch (e: UnknownHostException) {
            return StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null)
        } catch (e: SocketTimeoutException) {
            return StatusApi.error(context.getString(R.string.SocketTimeoutException), null)
        } catch (e: ConnectException) {
            return StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null)
        }
    }

    suspend fun downloadPicture(context: Context, pictureId: Long): StatusApi<ResponseBody> {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)

            apiInjector.downloadPicture(getHeader(context), pictureId).let {
                if (it.isSuccessful) {
                    return StatusApi.success("", it.body())
                } else {
                    return StatusApi.error("Impossible de charger l'image", it.body())
                }
            }
        } catch (e: UnknownHostException) {
            return StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null)
        } catch (e: SocketTimeoutException) {
            return StatusApi.error(context.getString(R.string.SocketTimeoutException), null)
        } catch (e: ConnectException) {
            return StatusApi.error(context.getString(R.string.UnknownHostException, IP, PORT), null)
        }
    }

    private fun getHeader(context: Context): Map<String, String> {
        val mutableMap = HashMap<String, String>()
        mutableMap[TOKEN] = context.getString(R.string.send_token, Token.value.toString())
        return mutableMap
    }
}