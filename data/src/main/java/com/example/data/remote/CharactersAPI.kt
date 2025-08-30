package com.example.data.remote


import com.example.data.model.characters_list.CharactersResponse
import com.example.data.model.characters_list.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface CharactersAPI {

    @GET("character")
    suspend fun getCharacters(
        @QueryMap queryMap: Map<String, String>
    ): Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Result>
}