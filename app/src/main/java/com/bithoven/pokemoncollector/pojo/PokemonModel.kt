package com.bithoven.pokemoncollector.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon_table")
data class PokemonModel(
    @PrimaryKey
    @SerializedName("name")
    var name:String="",
    @SerializedName("url")
    var url:String="",
)

