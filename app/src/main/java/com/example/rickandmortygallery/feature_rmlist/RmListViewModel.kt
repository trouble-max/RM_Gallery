package com.example.rickandmortygallery.feature_rmlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortygallery.data.models.RmListEntry
import com.example.rickandmortygallery.repository.RickAndMortyRepository
import com.example.rickandmortygallery.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RmListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private var currPage = 1

    var characterList = mutableStateOf<List<RmListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedCharacterList = listOf<RmListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadCharacterPaginated()
    }

    fun searchCharacterList(query: String) {
        val listToSearch = if(isSearchStarting) {
            characterList.value
        } else {
            cachedCharacterList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                characterList.value = cachedCharacterList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                cachedCharacterList = characterList.value
                isSearchStarting = false
            }
            characterList.value = results
            isSearching.value = true
        }
    }

    fun loadCharacterPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCharacterList(currPage)
            when(result) {
                is Resource.Success -> {
                    endReached.value = currPage * 20 >= result.data!!.info.count
                    val characterEntries = result.data.results.mapIndexed { _, entry ->
                        RmListEntry(entry.id, entry.name, entry.image, entry.status)
                    }
                    currPage++

                    loadError.value = ""
                    isLoading.value = false
                    characterList.value += characterEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {

                }
            }
        }
    }

}