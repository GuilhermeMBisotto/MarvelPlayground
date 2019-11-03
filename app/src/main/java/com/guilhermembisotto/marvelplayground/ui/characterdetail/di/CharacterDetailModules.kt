package com.guilhermembisotto.marvelplayground.ui.characterdetail.di

import com.guilhermembisotto.marvelplayground.ui.characterdetail.CharacterDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val characterDetailViewModelModule = module {
    viewModel { CharacterDetailViewModel(get()) }
}

fun getCharacterDetailModules() = listOf(characterDetailViewModelModule)
