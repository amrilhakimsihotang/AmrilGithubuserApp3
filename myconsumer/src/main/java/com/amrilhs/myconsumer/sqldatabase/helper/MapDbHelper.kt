package com.amrilhs.amrilgithubuserapp3.sqldatabase.helper

import android.database.Cursor
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.AVATAR
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.COMPANY
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FAVORITE
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FOLLOWERS
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FOLLOWING
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.LOCATION
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.NAME
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.REPOSITORY
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.USERNAME
import com.amrilhs.myconsumer.model.UserFavorite


object MapDbHelper {
    fun mapDbHelperCursorToArrayList(cursor: Cursor?): ArrayList<UserFavorite> {
        val listFavorite = ArrayList<UserFavorite>()

        cursor?.apply {
            while (moveToNext()) {
                val username =
                    getString(getColumnIndexOrThrow(USERNAME))
                val name =
                    getString(getColumnIndexOrThrow(NAME))
                val avatar =
                    getString(getColumnIndexOrThrow(AVATAR))
                val company =
                    getString(getColumnIndexOrThrow(COMPANY))
                val location =
                    getString(getColumnIndexOrThrow(LOCATION))
                val repository =
                    getString(getColumnIndexOrThrow(REPOSITORY))
                val followers =
                    getString(getColumnIndexOrThrow(FOLLOWERS))
                val following =
                    getString(getColumnIndexOrThrow(FOLLOWING))
                val isFav =
                    getString(getColumnIndexOrThrow(FAVORITE))

                listFavorite.add(
                    UserFavorite(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        isFav
                    )
                )
            }
        }
        return listFavorite
    }

}
