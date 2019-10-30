package com.guilhermembisotto.data.characters.remote.service

import com.guilhermembisotto.data.characters.model.CharactersResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApiService {

    @GET("v1/public/characters")
    suspend fun characters(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<CharactersResult>
}