package com.amrilhs.amrilgithubuserapp3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UsersFollowing(
    var avatar: String? = "",
    var username: String? = "",
    var name: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
): Parcelable