package com.guilhermembisotto.data.characters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(
    var path: String,
    var extension: String
) : Parcelable {

    fun urlPath(): String {
        return "$path.$extension"
    }
}