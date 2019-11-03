package com.guilhermembisotto.core.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.net.toUri
import androidx.core.util.Pair
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

inline fun <reified T : Any> newIntent(
    context: Context,
    noinline init: Intent.() -> Unit = {}
): Intent {
    val intent = Intent(context, T::class.java)
    intent.init()
    return intent
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    startActivity(newIntent<T>(this, init), options)
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    startActivityForResult(newIntent<T>(this, init), requestCode, options)
}

inline fun <reified T : Any> Activity.launchActivityForSharedElements(
    args: Bundle? = null,
    vararg pairs: Pair<View, String>,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this, init)
    args?.run {
        intent.putExtras(args)
    }

    val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this,
        *pairs
    )

    try {
        startActivity(intent, options.toBundle())
    } catch (e: Exception) {
        startActivity(intent)
    }
}

fun Activity.openLink(url: String) {
    startActivity(
        Intent(
            Intent.ACTION_VIEW,
            (url.toUri())
        )
    )
}