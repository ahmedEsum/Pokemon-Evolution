package com.bithoven.pokemoncollector.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bithoven.pokemoncollector.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PokemonCollector)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        val bNav: BottomNavigationView = findViewById(R.id.b_nav)
        val appBar = AppBarConfiguration(setOf(R.id.home_btn, R.id.saved_btn))
        val hostFrag = findNavController(R.id.fragment_host)
        setupActionBarWithNavController(hostFrag, appBar)
        bNav.setupWithNavController(hostFrag)
        bNav.setOnItemSelectedListener {
            if (it.itemId != bNav.selectedItemId) {
                NavigationUI.onNavDestinationSelected(it, hostFrag)
            }
            true
        }


    }
}