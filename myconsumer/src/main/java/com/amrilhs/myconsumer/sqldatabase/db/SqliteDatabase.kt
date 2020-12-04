package com.amrilhs.amrilgithubuserapp3.sqldatabase.db

import android.net.Uri
import android.provider.BaseColumns

internal class SqliteDatabase {

    companion object {
        const val AUTHORITY = "com.amrilhs.amrilgithubuserapp3"
        const val SCHEME = "content"
    }
    internal class FavoriteBaseColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_table"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val FAVORITE = "isFav"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(
               TABLE_NAME
            ).build()
        }
    }
}