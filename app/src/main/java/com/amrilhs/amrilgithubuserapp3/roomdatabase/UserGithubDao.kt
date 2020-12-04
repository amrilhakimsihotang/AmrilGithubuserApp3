package com.amrilhs.amrilgithubuserapp3.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub


@Dao
interface UserGithubDao {
    //fungsi insert entity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userGithub: UserGithub)

    @Delete
    suspend fun deleteUser(userGithub: UserGithub)

    @Query("DELETE FROM usergithub_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM usergithub_table ORDER BY id ASC")
    fun readAllData():LiveData<List<UserGithub>>
}