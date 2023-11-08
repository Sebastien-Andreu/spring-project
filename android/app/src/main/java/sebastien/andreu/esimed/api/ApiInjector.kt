package sebastien.andreu.esimed.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import sebastien.andreu.esimed.api.moduleApi.ApiHelper
import retrofit2.Response
import retrofit2.http.Part
import sebastien.andreu.esimed.api.response.AuthResponse
import sebastien.andreu.esimed.api.response.CreateRankResponse
import sebastien.andreu.esimed.api.response.CreateTierListResponse
import sebastien.andreu.esimed.api.response.RegisterResponse
import sebastien.andreu.esimed.api.response.UpdateRankResponse
import sebastien.andreu.esimed.api.response.UpdateTierListResponse
import sebastien.andreu.esimed.api.response.UploadPictureResponse
import sebastien.andreu.esimed.model.PictureShow
import sebastien.andreu.esimed.model.Rank
import sebastien.andreu.esimed.model.TierList
import javax.inject.Inject

class ApiInjector
@Inject constructor(private val apiHelper: ApiHelper){
    suspend fun signup(message: MutableMap<String, Any?>): Response<RegisterResponse> = apiHelper.signup(message)
    suspend fun login(message: MutableMap<String, Any?>): Response<AuthResponse> = apiHelper.login(message)

    suspend fun createTierList(header: Map<String, String>, message: MutableMap<String, Any?>): Response<CreateTierListResponse> = apiHelper.createTierList(header, message)
    suspend fun getAllTierList(headers: Map<String, String>, userId: Long): Response<List<TierList>> = apiHelper.getAllTierList(headers, userId)
    suspend fun updateTierList(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UpdateTierListResponse> = apiHelper.updateTierList(headers, message)

    suspend fun createRank(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<CreateRankResponse> = apiHelper.createRank(headers, message)
    suspend fun getRankByListId(headers: Map<String, String>, listId: Long): Response<List<Rank>> = apiHelper.getRankByListId(headers, listId)
    suspend fun updateRank(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UpdateRankResponse> = apiHelper.updateRank(headers, message)


    suspend fun getPictureByTag(headers: Map<String, String>, tag: String): Response<List<PictureShow>> = apiHelper.getPictureByTag(headers, tag)
    suspend fun getPictureByRank(headers: Map<String, String>, rankId: Long): Response<List<PictureShow>> = apiHelper.getPictureByRank(headers, rankId)
    suspend fun downloadPicture(headers: Map<String, String>, pictureId: Long): Response<ResponseBody> = apiHelper.downloadPicture(headers, pictureId)
    suspend fun uploadPicture(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UploadPictureResponse> = apiHelper.uploadPicture(headers, message)
    suspend fun uploadPicture2(headers: Map<String, String>,
                               @Part file: MultipartBody.Part,
                               @Part("tag") tag: RequestBody,
                               @Part("userId") userId: RequestBody,
                               @Part("rankId") rankId: RequestBody
    ): Response<UploadPictureResponse> = apiHelper.uploadPicture2(headers, file, tag, userId, rankId)
}