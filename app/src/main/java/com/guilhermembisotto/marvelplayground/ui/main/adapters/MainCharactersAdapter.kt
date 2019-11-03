package com.guilhermembisotto.marvelplayground.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guilhermembisotto.data.characters.model.Character
import com.guilhermembisotto.marvelplayground.BR
import com.guilhermembisotto.marvelplayground.databinding.ItemMainCharactersBinding
import com.guilhermembisotto.marvelplayground.ui.main.adapters.diif.CharactersDiffUtilCallback

class MainCharactersAdapter(
    private val onItemClicked: (View, Character?) -> Unit
) :
    PagedListAdapter<Character, MainCharactersAdapter.CharactersViewHolder>(
        CharactersDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder =
        CharactersViewHolder(
            ItemMainCharactersBinding.inflate(LayoutInflater.from(parent.context))
        )

    class CharactersViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }

        fun bind(
            adapter: MainCharactersAdapter
        ) {
            binding.setVariable(BR.adapter, adapter)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(this)
        getItem(position)?.run { holder.bind(this) }
    }

    fun onClicked(view: View, item: Character?) = onItemClicked(view, item)
}