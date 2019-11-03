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
        private const val LIMIT = 20
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

    private val coroutineScope = scope + dataSourceExceptionHandler

    override suspend fun getCharacter(id: Int): Response<CharactersResult>? =
        apiService.character(id)

    override suspend fun getCharacterComics(characterId: Int): Response<ComicDataWrapper>? =
        apiService.characterComics(characterId)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems = params.requestedLoadSize
        createRequest(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createRequest(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createRequest(page, page - 1, numberOfItems, null, callback)
    }

    override fun createRequest(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        coroutineScope.launch {
            updateState(State.LOADING)

            try {

                apiService.characters(requestedPage * requestedLoadSize, LIMIT).run {
                    when {
                        isSuccessful -> {
                            val characters = body()?.data?.results
                            updateState(State.DONE)

                            initialCallback?.run {
                                onResult(characters ?: listOf(), null, 0 + LIMIT)
                            }

                            callback?.run {
                                onResult(characters ?: listOf(), adjacentPage)
                            }
                        }
                        else -> {
                            updateState(State.ERROR)
                            initialCallback?.run {
                                onResult(listOf(), null, 0)
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
        coroutineScope.cancel()
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}