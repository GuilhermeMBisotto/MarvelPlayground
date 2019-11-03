package com.guilhermembisotto.data.characters.contract

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.Comic

interface CharactersRepository {

    fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Character>
    suspend fun getCharacter(id: Int): ArrayList<Character>?
    suspend fun getCharacterComics(characterId: Int): ArrayList<Comic>?
}