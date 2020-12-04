package com.amrilhs.myconsumer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersData(
    var avatar: String? = "",
    var username: String? = "",
    var name: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable