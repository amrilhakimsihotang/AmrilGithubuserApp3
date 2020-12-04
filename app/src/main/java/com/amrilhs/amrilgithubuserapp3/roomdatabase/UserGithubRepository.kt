package com.amrilhs.amrilgithubuserapp3.roomdatabase

import androidx.lifecycle.LiveData
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub

class UserGithubRepository(private val userGithubDao: UserGithubDao) {

    val readAllData : LiveData<List<UserGithub>> =userGithubDao.readAllData()

    suspend fun addUser(userGithub: UserGithub){
        userGithubDao.addUser(userGithub)
    }
    suspend fun deleteUser(userGithub: UserGithub){
        userGithubDao.deleteUser(userGithub)
    }
    suspend fun deleteAllUsers(){
        userGithubDao.deleteAllUsers()
    }
}