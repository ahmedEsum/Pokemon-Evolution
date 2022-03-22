package com.bithoven.pokemoncollector.pojo

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    var count:Int=0,
    @SerializedName("next")
    var next:String="",
    @SerializedName("previous")
    var previous:String="",
    @SerializedName("results")
    var results:List<PokemonModel> = emptyList(),
)
