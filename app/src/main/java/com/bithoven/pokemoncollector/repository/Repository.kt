package com.bithoven.pokemoncollector.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bithoven.pokemoncollector.database.DataBaseMethods
import com.bithoven.pokemoncollector.network.ApiCall
import com.bithoven.pokemoncollector.pagging.PaggingPokemon
import com.bithoven.pokemoncollector.pojo.PokemonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val LIMIT = 20
private const val LIMIT_DB = 10

@Singleton
class Repository @Inject constructor(
    private val apiCall: ApiCall,
    private val dataBaseMethods: DataBaseMethods
) {


    fun getApiCAllResultNew(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { PaggingPokemon(apiCall) }
        ).flow
    }

    fun getDBPokemonList(): Flow<PagingData<PokemonModel>> {
        val pagingSourceFactory = {dataBaseMethods.getAllPokemonNew()}
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT_DB,
                maxSize = LIMIT_DB+(LIMIT_DB*2),
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun insertChar(pokemonModel: PokemonModel) =
        dataBaseMethods.upsertCharacter(pokemonModel)

    suspend fun deleteChar(pokemonModel: PokemonModel) =
        dataBaseMethods.deleteCharacter(pokemonModel)

    suspend fun clearList() = dataBaseMethods.clearList()

    suspend fun getElement(qName: String) = dataBaseMethods.searchElement(qName)


}