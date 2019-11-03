package com.guilhermembisotto.core.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.guilhermembisotto.core.utils.ApiCodes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    private val viewModelExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(
                ">>>CoroutineExcpHndlr",
                "coroutineContext: $coroutineContext throwable: ${throwable.printStackTrace()}"
            )
        }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO + viewModelExceptionHandler

    private val _internetHandlerLiveData = MutableLiveData<Boolean>().apply { }
    val internetHandlerLiveData: LiveData<Boolean> =
        Transformations.map(_internetHandlerLiveData) { it }

    fun setInternetConnectionStatus(isConnected: Boolean) =
        _internetHandlerLiveData.postValue(isConnected)

    private val _apiErrorLiveData = MutableLiveData<Pair<ApiCodes, String?>>()
    val apiErrorLiveData =
        Transformations.map(_apiErrorLiveData) { it }

    fun setApiError(code: Int, message: String?) {
        val apiErrorCodes: ApiCodes = when (code) {
            400 -> ApiCodes.BAD_REQUEST
            401 -> ApiCodes.UNAUTHORIZED
            403 -> ApiCodes.FORBIDDEN
            404 -> ApiCodes.NOT_FOUND
            500 -> ApiCodes.INTERNAL_SERVER_ERROR
            503 -> ApiCodes.SERVICE_UNAVAILABLE
            else -> ApiCodes.BAD_REQUEST
        }
        _apiErrorLiveData.postValue(Pair(apiErrorCodes, message))
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}
