package sebastien.andreu.esimed.api.moduleApi

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
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
import java.io.File

interface ApiService {

    @Headers(SEND_TYPE_JSON)
    @POST(SIGNUP)
    suspend fun signup(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<RegisterResponse>
    @Headers(SEND_TYPE_JSON)
    @POST(LOGIN)
    suspend fun login(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<AuthResponse>

    @Headers(SEND_TYPE_JSON)
    @POST(TIER_LIST)
    suspend fun createTierList(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<CreateTierListResponse>
    @Headers(SEND_TYPE_JSON)
    @GET(GET_TIER_LIST)
    suspend fun getAllTierList(@HeaderMap headers: Map<String, String>, @Path("userId") userId: Long): Response<List<TierList>>
    @Headers(SEND_TYPE_JSON)
    @PUT(TIER_LIST)
    suspend fun updateTierList(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<UpdateTierListResponse>



    @Headers(SEND_TYPE_JSON)
    @POST(RANK)
    suspend fun createRank(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<CreateRankResponse>
    @Headers(SEND_TYPE_JSON)
    @GET(GET_RANK_LIST_ID)
    suspend fun getRankByListId(@HeaderMap headers: Map<String, String>, @Path("listId") listId: Long): Response<List<Rank>>
    @Headers(SEND_TYPE_JSON)
    @PUT(RANK)
    suspend fun updateRank(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<UpdateRankResponse>



    @Headers(SEND_TYPE_JSON)
    @GET(GET_PICTURE_BY_TAG)
    suspend fun getPictureByTag(@HeaderMap headers: Map<String, String>, @Path("tag") tag: String): Response<List<PictureShow>>
    @Headers(SEND_TYPE_JSON)
    @GET(GET_PICTURE_BY_RANK)
    suspend fun getPictureByRank(@HeaderMap headers: Map<String, String>, @Path("rankId") rankId: Long): Response<List<PictureShow>>
    @GET(DOWNLOAD_PICTURE)
    suspend fun downloadPicture(@HeaderMap headers: Map<String, String>, @Path("pictureId") pictureId: Long): Response<ResponseBody>
    @POST(UPLOAD_PICTURE)
    suspend fun uploadPicture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<UploadPictureResponse>

    @Multipart
    @POST(UPLOAD_PICTURE_2)
    suspend fun uploadPicture2(@HeaderMap headers: Map<String, String>,
                               @Part file: MultipartBody.Part,
                               @Part("tag") tag: RequestBody,
                               @Part("userId") userId: RequestBody,
                               @Part("rankId") rankId: RequestBody
    ): Response<UploadPictureResponse>
}
