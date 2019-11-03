package com.guilhermembisotto.marvelplayground.ui.main

import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.guilhermembisotto.core.base.BaseViewModel
import com.guilhermembisotto.data.State
import com.guilhermembisotto.data.characters.CharactersRepositoryImpl
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.remote.datasource.CharactersRemoteDataSource

class MainViewModel(private val repository: CharactersRepository) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 100
    }

    private var config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    private val _characters = liveData(coroutineContext) {
        emitSource(repository.initializedPagedListBuilder(config).build())
    }
    val characters = Transformations.map(_characters) { it }

    private val _state = liveData(coroutineContext) {
        emitSource(
            Transformations.switchMap<CharactersRemoteDataSource, State>(
                (repository as CharactersRepositoryImpl).dataSourceFactory.charactersDataSourceLiveData,
                CharactersRemoteDataSource::state
            )
        )
    }
    val state = Transformations.map(_state) { it }
}