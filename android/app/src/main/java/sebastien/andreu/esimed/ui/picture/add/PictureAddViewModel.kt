package sebastien.andreu.esimed.ui.picture.add

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import sebastien.andreu.esimed.R
import sebastien.andreu.esimed.api.ApiInjector
import sebastien.andreu.esimed.api.StatusApi
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.response.CreateTierListResponse
import sebastien.andreu.esimed.api.response.UploadPictureResponse
import sebastien.andreu.esimed.model.PictureShow
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.utils.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PictureAddViewModel
@Inject constructor(
    private val apiInjector: ApiInjector,
    private val hostSelectionInterceptor: HostSelectionInterceptor
) : ViewModel() {
    private val TAG: String = "PictureAddViewModel"

    val apiResponse: MutableLiveData<StatusApi<List<PictureShow>>> = MutableLiveData()
    val uploadPicture: MutableLiveData<StatusApi<UploadPictureResponse>> = MutableLiveData()

    suspend fun uploadPicture(context: Context, bitmap: Bitmap, tierList: TierList, rank: Rank) = viewModelScope.launch {
        try {
            val file = convertBitmapToFile(context, bitmap)
            val fileRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

            val tagPart = RequestBody.create("text/plain".toMediaTypeOrNull(), tierList.tag)
            val userIdPart = RequestBody.create("text/plain".toMediaTypeOrNull(), tierList.userId.toString())
            val rankIdPart = RequestBody.create("text/plain".toMediaTypeOrNull(), rank.rankId.toString())

            hostSelectionInterceptor.setConnectTimeout(15, TimeUnit.SECONDS)
            apiInjector.uploadPicture2(getHeader(context), filePart, tagPart, userIdPart, rankIdPart).let {
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


    private fun convertBitmapToFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "image.jpg")

        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            Log.e("ImageUploader", "Error converting bitmap to file: ${e.message}")
        }

        return file
    }

    private fun getHeader(context: Context): Map<String, String> {
        val mutableMap = HashMap<String, String>()
        mutableMap[TOKEN] = context.getString(R.string.send_token, Token.value.toString())
        return mutableMap
    }
}