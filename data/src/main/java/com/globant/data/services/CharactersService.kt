package com.globant.data.services

import com.globant.data.models.remote.response.CharactersResponse
import com.globant.shared.data.network.ApiCommon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CharactersService {
    @GET("v1/public/characters")
    fun getCharactersBySearch(
        @QueryMap options: HashMap<String, Any>
    ): Call<ApiCommon<CharactersResponse>>

    @GET("v1/public/characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<ApiCommon<CharactersResponse>>
}

