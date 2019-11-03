package com.guilhermembisotto.core.utils.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.guilhermembisotto.core.utils.bindingadapters.helpers.BindableAdapter

@BindingAdapter("bind:data")
fun <T> RecyclerView.setRecyclerViewProperties(data: T?) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).setData(data)
    }
}

@BindingAdapter("bind:data")
fun <T> ViewPager2.setRecyclerViewProperties(data: T?) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).setData(data)
    }
}