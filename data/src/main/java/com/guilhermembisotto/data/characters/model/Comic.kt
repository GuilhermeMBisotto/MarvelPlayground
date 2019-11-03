package com.guilhermembisotto.data.characters.model

data class Comic(
    val id: Int,
    val title: String,
    var urls: ArrayList<Url>,
    val thumbnail: Thumbnail
)