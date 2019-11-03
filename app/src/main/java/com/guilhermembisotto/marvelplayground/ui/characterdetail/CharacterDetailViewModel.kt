package com.guilhermembisotto.marvelplayground.ui.characterdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.guilhermembisotto.core.base.BaseViewModel
import com.guilhermembisotto.data.characters.contract.CharactersRepository
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.data.characters.model.CharacterParcelable
import com.guilhermembisotto.data.characters.model.Comic
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val repository: CharactersRepository) : BaseViewModel() {

    /**
     *
     * All delay is just to prevent render's problems in UI
     * and make animations like fade and adjustments in position more fluid
     *
     * **/

    private companion object {
        private const val urlTypeDetail = "detail"
    }

    private val _characterParcelable = MutableLiveData<CharacterParcelable>()
    val characterParcelable = Transformations.map(_characterParcelable) { it }

    private val _character = MutableLiveData<Character>()
    val character = Transformations.map(_character) { it }

    private val _contentVisibility = MutableLiveData<Boolean>()
    val contentVisibility = Transformations.map(_contentVisibility) { it }

    private val _pageVisibility = MutableLiveData<Boolean>()
    val pageVisibility = Transformations.map(_pageVisibility) { it }

    private val _comics = MutableLiveData<ArrayList<Comic>>()
    val comics = Transformations.map(_comics) { it }

    fun setCharacter(character: CharacterParcelable) = launch {
        _characterParcelable.postValue(character)

        delay(500)
        getCharactersDetail(character.id)
    }

    private fun getCharactersDetail(id: Int) = launch {
        val response = repository.getCharacter(id)

        if (!response.isNullOrEmpty()) {
            delay(500)
            _character.postValue(response.first())
            _contentVisibility.postValue(true)
        } else {
            _contentVisibility.postValue(false)
        }
    }

    fun getCharacterComics(characterId: Int) = launch {

        val response = repository.getCharacterComics(characterId)
        delay(500)

        if (!response.isNullOrEmpty()) {
            _comics.postValue(response)
            _pageVisibility.postValue(true)
        } else {
            _pageVisibility.postValue(false)
        }
    }

    fun extractCorrectlyUrl(comic: Comic): String? =
        comic.urls.firstOrNull { it.type == urlTypeDetail }?.run {
            this.url
        } ?: run {
            null
        }
}