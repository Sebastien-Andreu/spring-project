package sebastien.andreu.esimed.api.moduleApi

import sebastien.andreu.esimed.api.response.ResponseApi
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers(SEND_TYPE_JSON)
    @POST(SIGNUP)
    suspend fun signup(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
    @Headers(SEND_TYPE_JSON)
    @POST(LOGIN)
    suspend fun login(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>

//    @Headers(SEND_TYPE_JSON)
//    @POST(CLIENT)
//    suspend fun createClient(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @GET(CLIENT)
//    suspend fun getAllClient(@HeaderMap headers: Map<String, String>): Response<List<Client>>
//    @Headers(SEND_TYPE_JSON)
//    @DELETE(CLIENT)
//    suspend fun deleteClient(@HeaderMap headers: Map<String, String>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @PUT(CLIENT)
//    suspend fun updateClient(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
//
//
//    @Headers(SEND_TYPE_JSON)
//    @POST(PROJECT)
//    suspend fun createProject(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @GET(PROJECT)
//    suspend fun getAllProject(@HeaderMap headers: Map<String, String>): Response<List<Project>>
//    @Headers(SEND_TYPE_JSON)
//    @DELETE(PROJECT)
//    suspend fun deletProject(@HeaderMap headers: Map<String, String>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @PUT(PROJECT)
//    suspend fun updateProject(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
//
//
//    @Headers(SEND_TYPE_JSON)
//    @POST(FACTURE)
//    suspend fun createFacture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @GET(FACTURE)
//    suspend fun getAllFacture(@HeaderMap headers: Map<String, String>): Response<List<Facture>>
//    @Headers(SEND_TYPE_JSON)
//    @DELETE(FACTURE)
//    suspend fun deletFacture(@HeaderMap headers: Map<String, String>): Response<ResponseApi>
//    @Headers(SEND_TYPE_JSON)
//    @PUT(FACTURE)
//    suspend fun updateFacture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi>
}