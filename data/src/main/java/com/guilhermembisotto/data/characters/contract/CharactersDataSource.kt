package com.guilhermembisotto.data.characters.contract

import androidx.paging.PageKeyedDataSource
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.CharactersResult
import com.guilhermembisotto.data.characters.model.ComicDataWrapper
import retrofit2.Response

interface CharactersDataSource {

    interface Remote {

        suspend fun getCharacter(id: Int): Response<CharactersResult>?
        suspend fun getCharacterComics(characterId: Int): Response<ComicDataWrapper>?

        fun createRequest(
            requestedPage: Int,
            adjacentPage: Int,
            requestedLoadSize: Int,
            initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, Character>?,
            callback: PageKeyedDataSource.LoadCallback<Int, Character>?
        )

        fun invalidate()
    }
}