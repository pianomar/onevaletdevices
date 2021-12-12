package com.omarhezi.valetdevices.deviceslist.api

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class APIModule {

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
}

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url

        return when (url.encodedPath) {
            ALL_DEVICES_ENDPOINT -> {
                val response: String = readJsonFile(ALL_DEVICES_ENDPOINT)
                return Response.Builder()
                    .code(200)
                    .message(response)
                    .request(chain.request())
                    .body(response.toResponseBody(response.toMediaTypeOrNull()))
                    .addHeader("content-type", "application/json")
                    .build()
            }
            else -> Response.Builder()
                .code(404)
                .message("Error")
                .request(chain.request())
                .addHeader("content-type", "application/json")
                .build()
        }
    }

    private fun readJsonFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }
    }

    companion object {
        const val ALL_DEVICES_ENDPOINT = "v1/all-devices"
    }
}