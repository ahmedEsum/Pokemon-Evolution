package com.bithoven.pokemoncollector.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bithoven.pokemoncollector.network.ApiCall
import com.bithoven.pokemoncollector.pojo.PokemonModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


private const val START_PAGE = 0

class PaggingPokemon @Inject constructor(private val apiCall: ApiCall) :
    PagingSource<Int, PokemonModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val page = params.key ?: START_PAGE
        return try {
            val response = apiCall.getPokeListOne(page, params.loadSize)
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (getOffset(response.body()!!.previous) !=null) getOffset(response.body()!!.previous)!! else null,
                nextKey = if (getOffset(response.body()!!.next) !=null) getOffset(response.body()!!.next)!! else null
            )
        }catch (e:IOException){
            return LoadResult.Error(e)
        }catch (e:HttpException){
            return LoadResult.Error(e)
        }
    }

    private fun getOffset (str:String?):Int? {
        str?.let {
            return str.split("=", "&")[1].toInt()
        }
        return null
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition
    }
}