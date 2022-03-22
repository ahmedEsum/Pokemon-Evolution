package com.bithoven.pokemoncollector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bithoven.pokemoncollector.R
import com.bithoven.pokemoncollector.databinding.PokemonItemBinding
import com.bithoven.pokemoncollector.pojo.PokemonModel
import com.bithoven.pokemoncollector.viewmodel.PokemonViewModel

class PagingAdapterPokemon(
    private var showDeleteButton: Boolean = false,
    var pokemonViewModel: PokemonViewModel
) : PagingDataAdapter<PokemonModel, PagingAdapterPokemon.PokemonViewHolder>(differ) {


    companion object {
        private val differ = object : DiffUtil.ItemCallback<PokemonModel>() {
            override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentPokemonModel = getItem(position)
        currentPokemonModel?.let {
            if (!showDeleteButton) {
                holder.bind(
                    currentPokemonModel,
                    pokemonViewModel.searchForElement(currentPokemonModel.name)
                )
            } else {
                holder.bind(currentPokemonModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), showDeleteButton, pokemonViewModel
        )
    }


    class PokemonViewHolder(
        private var pokemonItemBinding: PokemonItemBinding,
        showDeleteButton: Boolean = false,
        pokViewModel: PokemonViewModel
    ) :
        RecyclerView.ViewHolder(pokemonItemBinding.root) {
        init {
            if (showDeleteButton) {
                pokemonItemBinding.deleteButton.visibility = View.VISIBLE
                pokemonItemBinding.imageButton.visibility = View.GONE
            }
            pokemonItemBinding.adapterItemViewModel = pokViewModel
        }

        fun bind(pokemonModel: PokemonModel, cachedItem: Boolean = false) {
            if (cachedItem) {
                pokemonItemBinding.imageButton.setImageResource(R.drawable.ic_baseline_favorite_24_red)
            } else {
                pokemonItemBinding.imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            pokemonItemBinding.pokemonItem = pokemonModel
        }
    }
}