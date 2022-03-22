package com.bithoven.pokemoncollector.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.bithoven.pokemoncollector.adapter.PagingAdapterPokemon
import com.bithoven.pokemoncollector.adapter.StateLoadAdapter
import com.bithoven.pokemoncollector.databinding.FragmentHomeBinding
import com.bithoven.pokemoncollector.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class Home : Fragment() {
    private val  pokemoneViewModel: PokemonViewModel by viewModels()
    private lateinit var adapter: PagingAdapterPokemon
    private var fragmentHomeBinding: FragmentHomeBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        setUpViews()
        lifecycleScope.launchWhenStarted {
            pokemoneViewModel.pokemons.collectLatest {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener { loadState ->
            fragmentHomeBinding!!.RV.isVisible = loadState.source.refresh is LoadState.NotLoading
            fragmentHomeBinding!!.pr.isVisible = loadState.source.refresh is LoadState.Loading
            fragmentHomeBinding!!.retryButton.isVisible =
                loadState.source.refresh is LoadState.Error
        }
        fragmentHomeBinding!!.retryButton.setOnClickListener {
            adapter.retry()
        }

        return fragmentHomeBinding!!.root
    }

    private fun setUpViews() {
        fragmentHomeBinding!!.lifecycleOwner = viewLifecycleOwner
        adapter = PagingAdapterPokemon(false, pokemoneViewModel)
        fragmentHomeBinding!!.RV.adapter = adapter.withLoadStateHeaderAndFooter(
            header = StateLoadAdapter { adapter.retry() },
            footer = StateLoadAdapter { adapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }



}