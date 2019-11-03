package com.guilhermembisotto.core.utils.bindingadapters.helpers

import androidx.appcompat.widget.AppCompatImageView
import coil.Coil
import coil.api.get
import coil.api.load
import coil.size.Scale
import com.guilhermembisotto.core.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun loadImage(view: AppCompatImageView, url: String, imageScale: Scale?) {
    CoroutineScope(Dispatchers.IO).launch {
        val drawable = Coil.get(url)

        withContext(Dispatchers.Main) {
            view.load(drawable) {
                crossfade(true)
                placeholder(R.drawable.ic_marvel_comics)
                error(R.drawable.ic_marvel_comics)
                imageScale?.run { scale(this) }
            }
        }
    }
}