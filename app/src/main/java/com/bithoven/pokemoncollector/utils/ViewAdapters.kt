package com.bithoven.pokemoncollector.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bithoven.pokemoncollector.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, img_url: String?) {
    img_url?.let {
        val lista = it.split("/")
        val newUrl = "https://cdn.traction.one/pokedex/pokemon/${lista[lista.size - 2]}.png"
        Glide.with(imageView.context).load(newUrl).apply {
            RequestOptions().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_error_24)
        }.into(imageView)
    }

}