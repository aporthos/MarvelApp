package com.globant.shared.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.globant.shared.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.character_default)
        .fit()
        .centerCrop()
        .into(view)
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}