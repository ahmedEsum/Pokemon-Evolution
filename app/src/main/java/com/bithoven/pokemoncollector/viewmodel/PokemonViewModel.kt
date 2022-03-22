package com.bithoven.pokemoncollector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bithoven.pokemoncollector.pojo.PokemonModel
import com.bithoven.pokemoncollector.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var pokemons :Flow<PagingData<PokemonModel>> = repository.getApiCAllResultNew().cachedIn(viewModelScope)

    fun insertPokemon(pokemonModel: PokemonModel) {
        viewModelScope.launch {
            repository.insertChar(pokemonModel)
        }
    }

    fun deletePokemon(pokemonModel: PokemonModel) {
        viewModelScope.launch {
            repository.deleteChar(pokemonModel)
        }
    }

    fun clearPokemonList() {
        viewModelScope.launch {
            repository.clearList()
        }
    }

    fun getPokemonListNew() = repository.getDBPokemonList()

    fun searchForElement(qName: String): Boolean {
        var found: Boolean
        runBlocking {
            found = repository.getElement(qName)
        }
        return found
    }
}