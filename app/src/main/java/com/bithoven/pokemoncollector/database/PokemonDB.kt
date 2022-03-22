package com.bithoven.pokemoncollector.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bithoven.pokemoncollector.pojo.PokemonModel

@Database(entities = [PokemonModel::class],version = 2,exportSchema = false)
abstract class PokemonDB: RoomDatabase() {
    abstract fun getPokemonDao():DataBaseMethods
}