package com.guilhermembisotto.data.characters

import com.guilhermembisotto.data.characters.contract.CharactersDataSource
import com.guilhermembisotto.data.characters.contract.CharactersRepository

class CharactersRepositoryImpl(
    private val remote: CharactersDataSource.Remote
) : CharactersRepository {

}