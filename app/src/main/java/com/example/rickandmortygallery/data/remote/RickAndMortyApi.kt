package com.example.rickandmortygallery.data.remote

import com.example.rickandmortygallery.data.remote.responses.Character
import com.example.rickandmortygallery.data.remote.responses.CharacterList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacterList (
        @Query("page") page: Int
    ): CharacterList

    @GET("character/{id}")
    suspend fun getCharacterDetail (
        @Path("id") id: Int
    ): Character
}