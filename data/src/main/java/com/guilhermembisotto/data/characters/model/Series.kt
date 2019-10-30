package com.guilhermembisotto.data.characters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Series(
    var available: Int,
    var collectionURI: String,
    var items: ArrayList<Resource>
) : Parcelable