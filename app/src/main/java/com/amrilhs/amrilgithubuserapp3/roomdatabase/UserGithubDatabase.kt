package com.amrilhs.amrilgithubuserapp3.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub


@Database(entities = [UserGithub::class], version = 1, exportSchema = false)
abstract class UserGithubDatabase : RoomDatabase() {

    abstract fun userGithubDao(): UserGithubDao

    companion object {
        @Volatile
        private var INSTANCE: UserGithubDatabase? = null

        fun getDatabase(context: Context): UserGithubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(lock = this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, UserGithubDatabase::class.java,
                    "usergithub_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}