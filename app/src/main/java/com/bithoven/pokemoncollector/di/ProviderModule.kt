package com.bithoven.pokemoncollector.di

import android.app.Application
import androidx.room.Room
import com.bithoven.pokemoncollector.database.DataBaseMethods
import com.bithoven.pokemoncollector.database.PokemonDB
import com.bithoven.pokemoncollector.network.ApiCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun getRetrofitBuilder(): ApiCall {
        return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiCall::class.java)
    }


    @Provides
    @Singleton
    fun getDataBaseInstance(application: Application) =
        Room.databaseBuilder(application.applicationContext, PokemonDB::class.java, "pokemonDB")
            .fallbackToDestructiveMigration().build()


    @Provides
    @Singleton
    fun getPokemonDao(pokemonDB: PokemonDB): DataBaseMethods {
        return pokemonDB.getPokemonDao()
    }

}