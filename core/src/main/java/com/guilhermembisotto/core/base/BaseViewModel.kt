package com.guilhermembisotto.core.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.guilhermembisotto.core.utils.ApiCode
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

    private val _apiErrorLiveData = MutableLiveData<Pair<ApiCode, String?>>()
    val apiErrorLiveData =
        Transformations.map(_apiErrorLiveData) { it }

    fun setApiError(code: Int, message: String?) {
        val apiErrorCode: ApiCode = when (code) {
            400 -> ApiCode.BAD_REQUEST
            401 -> ApiCode.UNAUTHORIZED
            403 -> ApiCode.FORBIDDEN
            404 -> ApiCode.NOT_FOUND
            500 -> ApiCode.INTERNAL_SERVER_ERROR
            503 -> ApiCode.SERVICE_UNAVAILABLE
            else -> ApiCode.BAD_REQUEST
        }
        _apiErrorLiveData.postValue(Pair(apiErrorCode, message))
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}
