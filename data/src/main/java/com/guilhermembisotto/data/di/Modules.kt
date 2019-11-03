package com.guilhermembisotto.data.di

import com.guilhermembisotto.data.RetrofitInitializer
import com.guilhermembisotto.data.characters.CharactersRepositoryImpl
import com.guilhermembisotto.data.characters.contract.CharactersDataSource
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.remote.datasource.CharactersRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.module.Module
import org.koin.dsl.module

private val apiServiceModule = module {
    single { RetrofitInitializer().charactersApiService() }
}

private val repositoryModule = module {
    single<CharactersRepository> { CharactersRepositoryImpl(get()) }
}

private val dataSourceModule = module {
    single<CharactersDataSource.Remote> {
        CharactersRemoteDataSource(
            scope = getProperty(COROUTINES_PROPERTY),
            apiService = get()
        )
    }
}

private const val COROUTINES_PROPERTY = "CoroutinesScope"
private val job = Job()
private val coroutinesProperties =
    mapOf<String, Any>(Pair(COROUTINES_PROPERTY, CoroutineScope(job + Dispatchers.IO)))

fun getDataModules(): List<Module> = listOf(apiServiceModule, dataSourceModule, repositoryModule)

fun getDataProperties(): Map<String, Any> = coroutinesProperties