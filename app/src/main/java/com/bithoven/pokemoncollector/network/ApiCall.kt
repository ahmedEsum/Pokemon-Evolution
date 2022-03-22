package com.bithoven.pokemoncollector.network

import com.bithoven.pokemoncollector.pojo.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {

    @GET("pokemon")
    suspend fun getPokeListOne(
    @Query("offset") offset:Int,
    @Query("limit") limit:Int
    ):Response<PokemonResponse>




}