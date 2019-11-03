package com.guilhermembisotto.core.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.guilhermembisotto.core.utils.extensions.activityBinding
import com.guilhermembisotto.core.utils.extensions.animateTransitionOnRebind
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes val resId: Int
) : AppCompatActivity(), CoroutineScope {

    // NetworkChangeReceiver.ConnectivityReceiverListener

    private val activityExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(
                ">>>CoroutineExcpHndlr",
                "coroutineContext: $coroutineContext throwable: ${throwable.printStackTrace()}"
            )
        }

    var dialog: Dialog? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + activityExceptionHandler

    // private var networkChangeReceiver = NetworkChangeReceiver()

    val binding by activityBinding<T>(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.animateTransitionOnRebind<ViewDataBinding>()

        subscribeUi()
    }

    override fun onResume() {
        super.onResume()
        // registerReceiver(
        //     networkChangeReceiver,
        //     IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        // )
        // NetworkChangeReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        // unregisterReceiver(networkChangeReceiver)
    }

    // override fun onNetworkConnectionChanged(isConnected: Boolean) {
    //     onConnectionChanged(isConnected)
    //     if (isConnected) {
    //         dialog?.dismiss()
    //     } else {
    //         if (dialog?.isShowing == false || dialog?.isShowing == null)
    //             dialog = showDialogConnectionError { }
    //     }
    // }

    /**
     * Override this method to observe livedata objects (optional)
     */
    open fun subscribeUi() {}

    /**
     * Override this method to check connectivity (optional)
     */
    open fun onConnectionChanged(isConnected: Boolean) {}

    fun replaceFragment(fragment: BaseFragment<*>, fragmentContainerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                fragmentContainerId,
                fragment
            )
            .commit()
    }
}