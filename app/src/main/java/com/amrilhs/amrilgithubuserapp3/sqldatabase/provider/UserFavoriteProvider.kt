package com.amrilhs.amrilgithubuserapp3.sqldatabase.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.Companion.AUTHORITY
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.CONTENT_URI
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.TABLE_NAME
import com.amrilhs.amrilgithubuserapp3.sqldatabase.helper.QueryDbHelper

class UserFavoriteProvider : ContentProvider() {
    companion object {
        private const val FAV = 1
        private const val FAV_ID = 2
        private lateinit var queryDbHelper: QueryDbHelper
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            sUriMatcher.addURI(
                AUTHORITY, "${TABLE_NAME}/#",
                FAV_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        queryDbHelper = QueryDbHelper.getInstance(context as Context)
        queryDbHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV -> queryDbHelper.readAll()
            FAV_ID -> queryDbHelper.queryByUsername(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(
        uri: Uri,
        values: ContentValues?
    ): Uri? {
        val insert: Long = when (FAV) {
            sUriMatcher.match(uri) -> queryDbHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$insert")
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> queryDbHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val updated: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> queryDbHelper.update(
                uri.lastPathSegment.toString(),
                values!!
            )
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

}