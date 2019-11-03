package com.guilhermembisotto.data.characters.remote.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.guilhermembisotto.data.State
import com.guilhermembisotto.data.characters.contract.CharactersDataSource
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.CharactersResult
import com.guilhermembisotto.data.characters.model.ComicDataWrapper
import com.guilhermembisotto.data.characters.remote.service.CharactersApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import retrofit2.Response

class CharactersRemoteDataSource(
    private val scope: CoroutineScope,
    private val apiService: CharactersApiService
) : PageKeyedDataSource<Int, Character>(), CharactersDataSource.Remote {

    companion object {
        private const val FIRST_PAGE = 0
        private const val PER_PAGE = 20
        private val TAG = CharactersRemoteDataSource::class.java.simpleName
    }

    var state: MutableLiveData<State> = MutableLiveData()

    private val dataSourceExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(
                TAG,
                "dataSourceExceptionHandler - coroutineContext: $coroutineContext throwable: ${throwable.printStackTrace()}"
            )
        }

    private val coroutinesScope = scope + dataSourceExceptionHandler

    override suspend fun getCharacter(id: Int): Response<CharactersResult>? =
        apiService.character(id)

    override suspend fun getCharacterComics(characterId: Int): Response<ComicDataWrapper>? =
        apiService.characterComics(characterId)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        createRequest(FIRST_PAGE, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        createRequest(params.key, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {}

    override fun createRequest(
        requestedPage: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        coroutinesScope.launch {
            updateState(State.LOADING)

            try {

                apiService.characters(requestedPage, PER_PAGE).run {
                    when {
                        isSuccessful -> {
                            val characters = body()?.data?.results
                            updateState(State.DONE)

                            initialCallback?.run {
                                onResult(characters ?: listOf(), null, FIRST_PAGE + PER_PAGE)
                            }

                            callback?.run {
                                onResult(characters ?: listOf(), requestedPage + PER_PAGE)
                            }
                        }
                        else -> {
                            updateState(State.ERROR)
                            initialCallback?.run {
                                onResult(listOf(), null, FIRST_PAGE)
                            }

                            callback?.run {
                                onResult(listOf(), requestedPage)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                updateState(State.ERROR)
                Log.e(TAG, "Characters Fetch Error: $e")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
        coroutinesScope.cancel()
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}