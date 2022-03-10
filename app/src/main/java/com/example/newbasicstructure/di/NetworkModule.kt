package com.example.newbasicstructure.di


import android.content.Context
import com.example.newbasicstructure.network.ApiRestService
import com.example.newbasicstructure.network.interceptor.HeaderInterceptor
import com.example.newbasicstructure.network.interceptor.NetworkInterceptor
import com.example.newbasicstructure.util.DeviceUtil
import com.example.newbasicstructure.util.PreferenceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jeetesh Surana.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getDeviceUtil(
        @ApplicationContext appContext: Context,
        preferenceProvider: PreferenceProvider
    ) = DeviceUtil(appContext, preferenceProvider)

    @Provides
    @Singleton
    fun getNetworkInterceptor(@ApplicationContext appContext: Context) =
        NetworkInterceptor(appContext)

    @Provides
    @Singleton
    fun getHeaderInterceptor(deviceUtil: DeviceUtil, preferenceProvider: PreferenceProvider) =
        HeaderInterceptor(deviceUtil, preferenceProvider)

    @Provides
    @Singleton
    fun getApiRestService(
        @ApplicationContext appContext: Context,
        networkInterceptor: NetworkInterceptor,
        headerInterceptor: HeaderInterceptor
    ) = ApiRestService(appContext, networkInterceptor, headerInterceptor)
}