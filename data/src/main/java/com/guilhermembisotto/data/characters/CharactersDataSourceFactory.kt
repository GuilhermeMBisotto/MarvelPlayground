package com.guilhermembisotto.data.characters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.remote.datasource.CharactersRemoteDataSource

class CharactersDataSourceFactory(
    private val remote: CharactersRemoteDataSource
) : DataSource.Factory<Int, Character>() {

    val charactersDataSourceLiveData = MutableLiveData<CharactersRemoteDataSource>()

    override fun create(): DataSource<Int, Character> {
        charactersDataSourceLiveData.postValue(remote)
        return remote
    }
}