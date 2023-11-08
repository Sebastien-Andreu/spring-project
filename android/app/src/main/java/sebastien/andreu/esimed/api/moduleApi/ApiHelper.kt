package sebastien.andreu.esimed.api.moduleApi

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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


interface ApiHelper {
    suspend fun signup(message: MutableMap<String, Any?>): Response<RegisterResponse>
    suspend fun login(message: MutableMap<String, Any?>): Response<AuthResponse>


    suspend fun createTierList(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<CreateTierListResponse>
    suspend fun getAllTierList(headers: Map<String, String>, userId: Long): Response<List<TierList>>
    suspend fun updateTierList(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UpdateTierListResponse>


    suspend fun createRank(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<CreateRankResponse>
    suspend fun getRankByListId(headers: Map<String, String>, listId: Long): Response<List<Rank>>
    suspend fun updateRank(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UpdateRankResponse>


    suspend fun getPictureByTag(headers: Map<String, String>, tag: String): Response<List<PictureShow>>
    suspend fun getPictureByRank(headers: Map<String, String>, rankId: Long): Response<List<PictureShow>>
    suspend fun downloadPicture(headers: Map<String, String>, pictureId: Long): Response<ResponseBody>
    suspend fun uploadPicture(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<UploadPictureResponse>
    suspend fun uploadPicture2(headers: Map<String, String>, @Part file: MultipartBody.Part,
                               @Part("tag") tag: RequestBody,
                               @Part("userId") userId: RequestBody,
                               @Part("rankId") rankId: RequestBody
    ): Response<UploadPictureResponse>
}