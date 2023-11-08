package sebastien.andreu.esimed.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import sebastien.andreu.esimed.api.interceptor.HostSelectionInterceptor
import sebastien.andreu.esimed.api.moduleApi.ApiHelper
import sebastien.andreu.esimed.api.moduleApi.ApiHelperImpl
import sebastien.andreu.esimed.api.moduleApi.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sebastien.andreu.esimed.BuildConfig
import sebastien.andreu.esimed.utils.PREFS_NAME
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesHostInterceptor(): HostSelectionInterceptor {
        return HostSelectionInterceptor.get()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(hostSelectionInterceptor: HostSelectionInterceptor) =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(hostSelectionInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BuildConfig.HOST)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}
