package sebastien.andreu.esimed.api.moduleApi

import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Part
import retrofit2.http.Path
import sebastien.andreu.esimed.api.response.AuthResponse
import sebastien.andreu.esimed.api.response.CreateRankResponse
import sebastien.andreu.esimed.api.response.CreateTierListResponse
import sebastien.andreu.esimed.api.response.RegisterResponse
import sebastien.andreu.esimed.api.response.UpdateRankResponse
import sebastien.andreu.esimed.api.response.UpdateTierListResponse
import sebastien.andreu.esimed.api.response.UploadPictureResponse
import sebastien.andreu.esimed.extension.sha1
import sebastien.andreu.esimed.model.PictureShow
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import sebastien.andreu.esimed.utils.SHA1
import javax.inject.Inject

class ApiHelperImpl
@Inject constructor( private val apiService: ApiService): ApiHelper {

    override suspend fun signup(@Body message: MutableMap<String, Any?>): Response<RegisterResponse> {
        return apiService.signup(getHeader(message), message)
    }

    override suspend fun login(@Body message: MutableMap<String, Any?>): Response<AuthResponse> {
        return apiService.login(getHeader(message), message)
    }

    override suspend fun createTierList(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<CreateTierListResponse> {
        return apiService.createTierList(headers, message)
    }
    override suspend fun getAllTierList(@HeaderMap headers: Map<String, String>, @Path("userId") userId: Long): Response<List<TierList>> {
        return apiService.getAllTierList(headers, userId)
    }
    override suspend fun updateTierList(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<UpdateTierListResponse> {
        return apiService.updateTierList(headers, message)
    }

    override suspend fun createRank(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<CreateRankResponse> {
        return apiService.createRank(headers, message)
    }
    override suspend fun getRankByListId(@HeaderMap headers: Map<String, String>, @Path("listId") listId: Long): Response<List<Rank>> {
        return apiService.getRankByListId(headers, listId)
    }

    override suspend fun updateRank(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UpdateRankResponse> {
        return apiService.updateRank(headers, message)
    }

    override suspend fun getPictureByTag(@HeaderMap headers: Map<String, String>, @Path("tag") tag: String): Response<List<PictureShow>> {
        return apiService.getPictureByTag(headers, tag)
    }

    override suspend fun getPictureByRank(@HeaderMap headers: Map<String, String>, @Path("rankId") rankId: Long): Response<List<PictureShow>> {
        return apiService.getPictureByRank(headers, rankId)
    }

    override suspend fun downloadPicture(@HeaderMap headers: Map<String, String>, @Path("pictureId") pictureId: Long): Response<ResponseBody> {
        return apiService.downloadPicture(headers, pictureId)
    }

    override suspend fun uploadPicture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<UploadPictureResponse> {
        return apiService.uploadPicture(headers, message)
    }

    override suspend fun uploadPicture2(@HeaderMap headers: Map<String, String>, @Part file: MultipartBody.Part,
                                        @Part("tag") tag: RequestBody,
                                        @Part("userId") userId: RequestBody,
                                        @Part("rankId") rankId: RequestBody
    ): Response<UploadPictureResponse> {
        return apiService.uploadPicture2(headers, file, tag, userId, rankId)
    }


    private fun getHeader(message: MutableMap<String, Any?>): Map<String, String> {
        val map = HashMap<String, String>()
        map[SHA1] = Gson().toJson(message).sha1
        return map
    }
}