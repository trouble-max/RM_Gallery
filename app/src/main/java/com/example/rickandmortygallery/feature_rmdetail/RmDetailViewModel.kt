package com.example.rickandmortygallery.feature_rmdetail

import androidx.lifecycle.ViewModel
import com.example.rickandmortygallery.data.remote.responses.Character
import com.example.rickandmortygallery.repository.RickAndMortyRepository
import com.example.rickandmortygallery.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RmDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    suspend fun getCharacterDetail(id: Int): Resource<Character> {
        return repository.getCharacterDetail(id)
    }
}