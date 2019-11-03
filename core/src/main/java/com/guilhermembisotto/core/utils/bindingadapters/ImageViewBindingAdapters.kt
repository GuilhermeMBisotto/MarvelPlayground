package com.guilhermembisotto.core.utils.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.size.Scale
import com.guilhermembisotto.core.utils.bindingadapters.helpers.loadImage

@BindingAdapter(
    "bind:imageSet",
    "bind:imageScale",
    requireAll = false
)
fun AppCompatImageView.imageFromUrl(
    imageUrl: String?,
    imageScale: Scale?
) {
    imageUrl?.let { loadImage(this, it, imageScale) }
}