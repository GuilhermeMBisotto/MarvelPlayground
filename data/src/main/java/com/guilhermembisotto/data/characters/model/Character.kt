package com.guilhermembisotto.data.characters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    var id: Int,
    var name: String,
    var description: String,
    var modified: String,
    var thumbnail: Thumbnail,
    var resourceURI: String,
    var comics: Comics,
    var series: Series,
    var stories: Stories,
    var events: Events,
    var urls: ArrayList<Url>
) : Parcelable {

    fun Character.toParcelable() = CharacterParcelable(
        id,
        name,
        thumbnail
    )
}

@Parcelize
data class CharacterParcelable(
    var id: Int,
    var name: String,
    var thumbnail: Thumbnail
) : Parcelable