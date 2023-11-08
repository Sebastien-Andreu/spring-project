package sebastien.andreu.esimed.api.moduleApi

import sebastien.andreu.esimed.api.response.ResponseApi
import retrofit2.Response


interface ApiHelper {
    suspend fun signup(message: MutableMap<String, Any?>): Response<ResponseApi>
    suspend fun login(message: MutableMap<String, Any?>): Response<ResponseApi>


//    suspend fun createClient(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
//    suspend fun getAllClient(headers: Map<String, String>): Response<List<Client>>
//    suspend fun deleteClient(headers: Map<String, String>): Response<ResponseApi>
//    suspend fun updateClient(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
//
//    suspend fun createProject(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
//    suspend fun getAllProject(headers: Map<String, String>): Response<List<Project>>
//    suspend fun deleteProject(headers: Map<String, String>): Response<ResponseApi>
//    suspend fun updateProject(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
//
//    suspend fun createFacture(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
//    suspend fun getAllFacture(headers: Map<String, String>): Response<List<Facture>>
//    suspend fun deleteFacture(headers: Map<String, String>): Response<ResponseApi>
//    suspend fun updateFacture(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi>
}