package com.bithoven.pokemoncollector.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.bithoven.pokemoncollector.pojo.PokemonModel
import kotlinx.coroutines.flow.Flow



@Dao
interface DataBaseMethods {

    @Insert(onConflict = REPLACE)
    suspend fun upsertCharacter(pokemonModel: PokemonModel)

    @Delete
    suspend fun deleteCharacter(pokemonModel: PokemonModel)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemonNew(): PagingSource<Int,PokemonModel>

    @Query("DELETE  FROM pokemon_table ")
    suspend fun clearList()

    @Query("SELECT EXISTS (SELECT 1 FROM pokemon_table WHERE name = :qName)")
    suspend fun searchElement(qName:String) : Boolean
}