package com.example.newbasicstructure.repository

import android.content.Context
import android.util.Log
import com.example.newbasicstructure.data.remote.model.response.PostResponse
import com.example.newbasicstructure.network.ApiRestService
import com.example.newbasicstructure.network.SafeApiRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.ResponseBody
import javax.inject.Inject

@ActivityRetainedScoped
class DemoRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: ApiRestService
) : SafeApiRequest(context) {

    suspend fun getWeather(): PostResponse {
        return apiRequest { api.getWeather() }
    }
}