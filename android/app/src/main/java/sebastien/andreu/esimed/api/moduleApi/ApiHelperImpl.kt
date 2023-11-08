package sebastien.andreu.esimed.api.moduleApi

import com.google.gson.Gson
import sebastien.andreu.esimed.api.response.ResponseApi
import retrofit2.Response
import retrofit2.http.Body
import sebastien.andreu.esimed.extension.sha1
import sebastien.andreu.esimed.utils.SHA1
import javax.inject.Inject

class ApiHelperImpl
@Inject constructor( private val apiService: ApiService): ApiHelper {

    override suspend fun signup(@Body message: MutableMap<String, Any?>): Response<ResponseApi> {
        return apiService.signup(getHeader(message), message)
    }

    override suspend fun login(@Body message: MutableMap<String, Any?>): Response<ResponseApi> {
        return apiService.login(getHeader(message), message)
    }

//    override suspend fun createClient(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.createClient(headers, message)
//    }
//    override suspend fun getAllClient(@HeaderMap headers: Map<String, String>): Response<List<Client>> {
//        return apiService.getAllClient(headers)
//    }
//    override suspend fun deleteClient(@HeaderMap headers: Map<String, String>): Response<ResponseApi> {
//        return apiService.deleteClient(headers)
//    }
//    override suspend fun updateClient(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.updateClient(headers, message)
//    }
//
//
//    override suspend fun createProject(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.createProject(headers, message)
//    }
//    override suspend fun getAllProject(@HeaderMap headers: Map<String, String>): Response<List<Project>> {
//        return apiService.getAllProject(headers)
//    }
//    override suspend fun deleteProject(@HeaderMap headers: Map<String, String>): Response<ResponseApi> {
//        return apiService.deletProject(headers)
//    }
//    override suspend fun updateProject(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.updateProject(headers, message)
//    }
//
//
//    override suspend fun createFacture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.createFacture(headers, message)
//    }
//    override suspend fun getAllFacture(@HeaderMap headers: Map<String, String>): Response<List<Facture>> {
//        return apiService.getAllFacture(headers)
//    }
//    override suspend fun deleteFacture(@HeaderMap headers: Map<String, String>): Response<ResponseApi> {
//        return apiService.deletFacture(headers)
//    }
//    override suspend fun updateFacture(@HeaderMap headers: Map<String, String>, @Body message: MutableMap<String, Any?>): Response<ResponseApi> {
//        return apiService.updateFacture(headers, message)
//    }


    private fun getHeader(message: MutableMap<String, Any?>): Map<String, String> {
        val map = HashMap<String, String>()
        map[SHA1] = Gson().toJson(message).sha1
        return map
    }
}