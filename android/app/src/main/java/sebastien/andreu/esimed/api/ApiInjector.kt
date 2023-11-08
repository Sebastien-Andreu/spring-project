package sebastien.andreu.esimed.api

import sebastien.andreu.esimed.api.moduleApi.ApiHelper
import sebastien.andreu.esimed.api.response.ResponseApi
import retrofit2.Response
import javax.inject.Inject

class ApiInjector
@Inject constructor(private val apiHelper: ApiHelper){
    suspend fun signup(message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.signup(message)
    suspend fun login(message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.login(message)

//    suspend fun createClient(header: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.createClient(header, message)
//    suspend fun getAllClient(headers: Map<String, String>): Response<List<Client>> = apiHelper.getAllClient(headers)
//    suspend fun deleteClient(headers: Map<String, String>): Response<ResponseApi> = apiHelper.deleteClient(headers)
//    suspend fun updateClient(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.updateClient(headers, message)
//
//    suspend fun createProject(header: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.createProject(header, message)
//    suspend fun getAllProject(headers: Map<String, String>): Response<List<Project>> = apiHelper.getAllProject(headers)
//    suspend fun deleteProject(headers: Map<String, String>): Response<ResponseApi> = apiHelper.deleteProject(headers)
//    suspend fun updateProject(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.updateProject(headers, message)
//
//
//    suspend fun createFacture(header: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.createFacture(header, message)
//    suspend fun getAllFacture(headers: Map<String, String>): Response<List<Facture>> = apiHelper.getAllFacture(headers)
//    suspend fun deleteFacture(headers: Map<String, String>): Response<ResponseApi> = apiHelper.deleteFacture(headers)
//    suspend fun updateFacture(headers: Map<String, String>, message: MutableMap<String, Any?>): Response<ResponseApi> = apiHelper.updateFacture(headers, message)
}