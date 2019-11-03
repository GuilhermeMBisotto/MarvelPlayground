package com.guilhermembisotto.data.characters

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.guilhermembisotto.data.characters.contract.CharactersDataSource
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.Comic
import com.guilhermembisotto.data.characters.remote.datasource.CharactersRemoteDataSource

class CharactersRepositoryImpl(
    private val remote: CharactersDataSource.Remote
) : CharactersRepository {

    val dataSourceFactory = CharactersDataSourceFactory(remote as CharactersRemoteDataSource)

    override suspend fun getCharacter(id: Int): ArrayList<Character>? =
        remote.getCharacter(id)?.body()?.data?.results

    override fun initializedPagedListBuilder(config: PagedList.Config):
        LivePagedListBuilder<Int, Character> =
        LivePagedListBuilder<Int, Character>(dataSourceFactory, config)

    override suspend fun getCharacterComics(characterId: Int): ArrayList<Comic>? =
        remote.getCharacterComics(characterId)?.body()?.data?.results
}