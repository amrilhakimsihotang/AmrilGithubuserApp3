package com.amrilhs.amrilgithubuserapp3.roomdatabase.roomviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.amrilhs.amrilgithubuserapp3.roomdatabase.UserGithubDatabase
import com.amrilhs.amrilgithubuserapp3.roomdatabase.UserGithubRepository
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UserGithub>>
    private val repository: UserGithubRepository

    init {
        val userGithubDao = UserGithubDatabase.getDatabase(application).userGithubDao()
        repository = UserGithubRepository(userGithubDao)
        readAllData = repository.readAllData
    }

    fun addUser(userGithub: UserGithub) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userGithub)
        }
    }

    fun deleteUser(userGithub: UserGithub) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userGithub)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}