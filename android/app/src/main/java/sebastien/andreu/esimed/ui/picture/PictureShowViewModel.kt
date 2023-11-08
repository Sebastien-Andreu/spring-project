package sebastien.andreu.esimed.ui.picture

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.ApiInjector
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.CreateTierListResponse
import sebastien.andreu.esimed.api.response.UploadPictureResponse
import sebastien.andreu.esimed.model.PictureShow
import sebastien.andreu.esimed.utils.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PictureShowViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "RankAddViewModel"

    val apiResponse: MutableLiveData<StatusApi<List<PictureShow>>> = MutableLiveData()
    val uploadPicture: MutableLiveData<StatusApi<UploadPictureResponse>> = MutableLiveData()

    suspend fun getPictureByTag(context: Context, tag: String) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.getPictureByTag(getHeader(context), tag).let {
                if (it.isSuccessful) {
                    apiResponse.postValue(StatusApi.success("", it.body()))
                } else {
                    Gson().fromJson(it.errorBody()!!.charStream(), CreateTierListResponse::class.java)?.let { errorResponse ->
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

    suspend fun uploadPicture(context: Context, pictureId: Long, rankId: Long) = viewModelScope.launch {
        try {
            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            val mutableMap = HashMap<String, Any?>()
            mutableMap["pictureId"] = pictureId
            mutableMap["rankId"] = rankId
            apiInjector.uploadPicture(getHeader(context), mutableMap).let {
                if (it.isSuccessful) {
                    uploadPicture.postValue(it.body()!!.message?.let { it1 -> StatusApi.success(it1, it.body()) })
                } else {
                    Gson().fromJson(it.errorBody()!!.charStream(), CreateTierListResponse::class.java)?.let { errorResponse ->
                        uploadPicture.postValue(errorResponse.error?.let { it1 -> StatusApi.error(it1, null) })
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