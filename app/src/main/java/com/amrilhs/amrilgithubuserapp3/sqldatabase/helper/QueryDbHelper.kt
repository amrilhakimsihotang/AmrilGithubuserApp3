package com.amrilhs.amrilgithubuserapp3.sqldatabase.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.TABLE_NAME
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.USERNAME
import java.sql.SQLException

class QueryDbHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dbQueryhelper: dbHelper
        private var INSTANCE: QueryDbHelper? = null
        fun getInstance(context: Context): QueryDbHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: QueryDbHelper(context)
        }

        private lateinit var sqliteDatabase: SQLiteDatabase
    }
    init {
        dbQueryhelper = dbHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        sqliteDatabase = dbQueryhelper.writableDatabase
    }

    fun close() {
        dbQueryhelper.close()
        if (sqliteDatabase.isOpen) {
            sqliteDatabase.close()
        }
    }

    fun readAll(): Cursor {
        return sqliteDatabase.query(
            DATABASE_TABLE, null, null, null, null, null, null
        )
    }

    fun queryByUsername(username: String): Cursor {
        return sqliteDatabase.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?", arrayOf(username),
            null, null, null, null
        )
    }

    fun insert(values: ContentValues?): Long {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues): Int {
        return sqliteDatabase.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }

    fun deleteById(id: String?): Int {
        return sqliteDatabase.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}