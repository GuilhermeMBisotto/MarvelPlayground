package com.guilhermembisotto.marvelplayground.ui.characterdetail.adapters

import android.view.View
import com.guilhermembisotto.marvelplayground.ui.base.BaseRecyclerViewAdapter
import com.guilhermembisotto.data.characters.model.Comic
import com.guilhermembisotto.marvelplayground.R

class CharacterComicsAdapter(
    private val onClicked: (view: View, obj: Comic) -> Unit
) : BaseRecyclerViewAdapter<Comic>(listOf()) {

    override fun getObjForPosition(position: Int): Any = getList()[position]
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_character_detail_comics
    override fun getAdapter(position: Int): BaseRecyclerViewAdapter<Comic> = this
    override fun getItemCount(): Int = getList().size

    fun onItemClicked(view: View, obj: Comic) = onClicked(view, obj)
}