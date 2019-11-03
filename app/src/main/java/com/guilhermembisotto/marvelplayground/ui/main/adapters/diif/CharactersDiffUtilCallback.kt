package com.guilhermembisotto.marvelplayground.ui.main.adapters.diif

import androidx.recyclerview.widget.DiffUtil
import com.guilhermembisotto.data.characters.model.Character

class CharactersDiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
            && oldItem.description == newItem.description
            && oldItem.modified == newItem.modified
            && oldItem.comics == newItem.comics
            && oldItem.events == newItem.events
            && oldItem.series == oldItem.series
            && oldItem.resourceURI == newItem.resourceURI
            && oldItem.stories == newItem.stories
            && oldItem.thumbnail == newItem.thumbnail
            && oldItem.urls == newItem.urls
    }
}