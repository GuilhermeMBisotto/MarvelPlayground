package com.guilhermembisotto.core.utils.extensions

import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import com.guilhermembisotto.core.utils.bindingproperties.ActivityBindingProperty

fun <T : ViewDataBinding> activityBinding(@LayoutRes resId: Int) =
    ActivityBindingProperty<T>(resId)

inline fun <reified T : ViewDataBinding> T.animateTransitionOnRebind() {
    addOnRebindCallback(object : OnRebindCallback<T>() {
        override fun onPreBind(binding: T?): Boolean {
            TransitionManager.beginDelayedTransition(binding?.root as ViewGroup)
            return super.onPreBind(binding)
        }
    })
}