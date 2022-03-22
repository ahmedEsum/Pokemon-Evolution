package com.bithoven.pokemoncollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bithoven.pokemoncollector.R
import com.bithoven.pokemoncollector.databinding.LoadStateItemBinding

class StateLoadAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<StateLoadAdapter.PokemonLoadSateAdapter>() {

    override fun onBindViewHolder(holder: PokemonLoadSateAdapter, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokemonLoadSateAdapter {
        return PokemonLoadSateAdapter.create(parent,retry)
    }

    class PokemonLoadSateAdapter(private val binding: LoadStateItemBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.retryButtonState.setOnClickListener { retry.invoke() }
            }
        fun bind (loadState : LoadState){
            if (loadState is LoadState.Error){
                binding.textErrorState.text = loadState.error.localizedMessage
            }
            binding.prState.isVisible = loadState is LoadState.Loading
            binding.textErrorState.isVisible = loadState !is LoadState.Loading
            binding.retryButtonState.isVisible = loadState !is LoadState.Loading
        }

        companion object{
            fun create(parent: ViewGroup,retry: () -> Unit) :PokemonLoadSateAdapter{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_item,parent,false)
                val binding = LoadStateItemBinding.bind(view)
                return PokemonLoadSateAdapter(binding,retry)
            }
        }
    }


}