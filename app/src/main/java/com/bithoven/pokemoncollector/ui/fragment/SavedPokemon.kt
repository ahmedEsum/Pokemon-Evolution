package com.bithoven.pokemoncollector.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bithoven.pokemoncollector.R
import com.bithoven.pokemoncollector.adapter.PagingAdapterPokemon
import com.bithoven.pokemoncollector.databinding.FragmentSavedPokemonBinding
import com.bithoven.pokemoncollector.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SavedPokemon : Fragment() {
    private val pokemoneViewModel: PokemonViewModel by viewModels()
    private lateinit var adapter: PagingAdapterPokemon

    private var binding: FragmentSavedPokemonBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedPokemonBinding.inflate(inflater)
        setUpViews()

        lifecycleScope.launchWhenStarted {
            pokemoneViewModel.getPokemonListNew().collectLatest {
                adapter.submitData(it)
            }
        }

        binding!!.fbClearAll.setOnClickListener {

            showDialog()
        }
        return binding!!.root
    }

    private fun setUpViews() {
        binding!!.lifecycleOwner = viewLifecycleOwner
        adapter = PagingAdapterPokemon(true, pokemoneViewModel)
        binding!!.RV.adapter = adapter
    }

    private fun showDialog() {
        val alert = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.alert_messagage)
            setMessage(R.string.alert_title)
            setCancelable(true)
            setNegativeButton("Cancel", null)
            setPositiveButton("Delete") { _, _ ->
                pokemoneViewModel.clearPokemonList()
            }
        }.create()
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}