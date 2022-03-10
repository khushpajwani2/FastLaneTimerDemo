package com.example.newbasicstructure.network

import android.content.Context
import android.util.Log
import com.example.newbasicstructure.R
import com.example.newbasicstructure.data.remote.model.response.PostResponse
import com.example.newbasicstructure.network.interceptor.HeaderInterceptor
import com.example.newbasicstructure.network.interceptor.NetworkInterceptor
import com.example.newbasicstructure.util.Constant.baseURL
import com.example.newbasicstructure.util.PreferenceProvider
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Jeetesh Surana.
 */

interface ApiRestService {

    @GET("posts")
    suspend fun getWeather(): Response<PostResponse>


    companion object {
        operator fun invoke(
            context: Context,
            networkInterceptor: NetworkInterceptor,
            headerInterceptor: HeaderInterceptor
        ): ApiRestService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val cacheSize = 20 * 1024 * 1024L // 20 MB

            fun provideCache(): Cache? {
                var cache: Cache? = null
                try {
                    cache = Cache(File(context.cacheDir, "Cache_directory"), cacheSize)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return cache
            }

            val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(provideCache())
                .addInterceptor(networkInterceptor)
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)


            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpBuilder.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRestService::class.java)
        }
    }
}
