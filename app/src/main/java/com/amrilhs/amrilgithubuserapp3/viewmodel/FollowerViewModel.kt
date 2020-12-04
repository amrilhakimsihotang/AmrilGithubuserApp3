package com.amrilhs.amrilgithubuserapp3.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amrilhs.amrilgithubuserapp3.model.UsersFollower
import com.amrilhs.amrilgithubuserapp3.utilities.*
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowerViewModel : ViewModel() {
    private val listFollower = ArrayList<UsersFollower>()
    private val listFollowerMutable = MutableLiveData<ArrayList<UsersFollower>>()

    fun getAllFollower(): LiveData<ArrayList<UsersFollower>> {
        return listFollowerMutable
    }

    fun getDataGit(context: Context, id: String) {
        val FollowerURL = "https://api.github.com/users/$id/followers"
        httpClient.addHeader(AUTH_AGENT, TOKEN)
        httpClient.addHeader(AUTH_USER, REQ_ACCESS)
        httpClient.get(FollowerURL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowerFragment.TAG, result!!)

                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getUserDetail(usernameLogin, context)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getUserDetail(usernameLogin: String, context: Context) {
        val urlGetDetail="https://api.github.com/users/$usernameLogin"
        httpClient.addHeader(AUTH_AGENT, TOKEN)
        httpClient.addHeader(AUTH_USER, REQ_ACCESS)
        httpClient.get(urlGetDetail, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowerFragment.TAG, result!!)
                try {
                    val jsonObject = JSONObject(result)
                    val usersFollower = UsersFollower()
                    usersFollower.username = jsonObject.getString("login")
                    usersFollower.name = jsonObject.getString("name")
                    usersFollower.avatar = jsonObject.getString("avatar_url")
                    usersFollower.company = jsonObject.getString("company")
                    usersFollower.location = jsonObject.getString("location")
                    usersFollower.repository = jsonObject.getString("public_repos")
                    usersFollower.followers = jsonObject.getString("followers")
                    usersFollower.following = jsonObject.getString("following")
                    listFollower.add(usersFollower)
                    listFollowerMutable.postValue(listFollower)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}