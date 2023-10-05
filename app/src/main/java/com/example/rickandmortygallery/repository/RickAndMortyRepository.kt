package com.example.rickandmortygallery.repository

import com.example.rickandmortygallery.data.remote.RickAndMortyApi
import com.example.rickandmortygallery.data.remote.responses.Character
import com.example.rickandmortygallery.data.remote.responses.CharacterList
import com.example.rickandmortygallery.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApi
) {

    suspend fun getCharacterList (page: Int) : Resource<CharacterList> {
        val response = try {
            api.getCharacterList(page)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getCharacterDetail (id: Int) : Resource<Character> {
        val response = try {
            api.getCharacterDetail(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}