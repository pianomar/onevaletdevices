package com.omarhezi.valetdevices.deviceslist.api

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Singleton
    @Provides
    fun providesMockInterceptor(@ApplicationContext context: Context) = MockInterceptor(context)

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: MockInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(MockInterceptor.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): DevicesAPI = retrofit.create(DevicesAPI::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: DevicesAPI) = DevicesListRepository(apiService)
}

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url

        return when (url.encodedPath) {
            "/$ALL_DEVICES_ENDPOINT" -> {
                val response = readJsonFile(ALL_DEVICES_ENDPOINT)
                return Response.Builder()
                    .code(200)
                    .message(response)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(response.toResponseBody(response.toMediaTypeOrNull()))
                    .addHeader("content-type", "application/json")
                    .build()
            }
            else -> {
                val response = readJsonFile(ERROR_FILE)
                Response.Builder()
                    .code(404)
                    .message(response)
                    .request(chain.request())
                    .body(response.toResponseBody(response.toMediaTypeOrNull()))
                    .protocol(Protocol.HTTP_1_1)
                    .addHeader("content-type", "application/json")
                    .build()
            }
        }
    }

    private fun readJsonFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }
    }

    companion object {
        const val BASE_URL = "https://localhost/"
        const val ALL_DEVICES_ENDPOINT = "deviceslist"
        const val ERROR_FILE = "errorbody"
    }
}