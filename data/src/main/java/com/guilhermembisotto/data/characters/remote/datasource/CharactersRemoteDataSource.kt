package com.guilhermembisotto.data.characters.remote.datasource

import com.guilhermembisotto.data.characters.contract.CharactersDataSource
import com.guilhermembisotto.data.characters.remote.service.CharactersApiService

class CharactersRemoteDataSource(
    private val apiService: CharactersApiService
) : CharactersDataSource.Remote {

}