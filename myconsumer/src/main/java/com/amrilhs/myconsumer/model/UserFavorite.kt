package com.amrilhs.myconsumer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserFavorite(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = "",
    var isFav: String? = ""
) : Parcelable
