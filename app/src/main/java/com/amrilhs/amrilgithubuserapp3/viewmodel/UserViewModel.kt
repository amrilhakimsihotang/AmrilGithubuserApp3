package com.amrilhs.amrilgithubuserapp3.viewmodel


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amrilhs.amrilgithubuserapp3.BottomNavigationActivity
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.amrilhs.amrilgithubuserapp3.utilities.*
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class UserViewModel : ViewModel() {
    private val listUsers = ArrayList<UsersData>()
    private val listUserMutable = MutableLiveData<ArrayList<UsersData>>()

    fun getAllUsers(): LiveData<ArrayList<UsersData>> {
        return listUserMutable
    }

    fun getGitDataUser(context: Context) {

        httpClient.addHeader(AUTH_AGENT, TOKEN)
        httpClient.addHeader(AUTH_USER, REQ_ACCESS)
        httpClient.get(BASE_URL,
            object : AsyncHttpResponseHandler() {

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    val result = responseBody?.let { String(it) }
                    Log.d(BottomNavigationActivity.TAG, result!!)
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

        httpClient.addHeader(AUTH_AGENT, TOKEN)
        httpClient.addHeader(AUTH_USER, REQ_ACCESS)
        val urlGetDetail = "https://api.github.com/users/$usernameLogin"

        httpClient.get(urlGetDetail, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(BottomNavigationActivity.TAG, result!!)
                try {
                    val jsonObject = JSONObject(result)
                    val usersData = UsersData()
                    usersData.avatar = jsonObject.getString("avatar_url")
                    usersData.username = jsonObject.getString("login")
                    usersData.name = jsonObject.getString("name")
                    usersData.company = jsonObject.getString("company")
                    usersData.location = jsonObject.getString("location")
                    usersData.repository = jsonObject.getString("public_repos")
                    usersData.followers = jsonObject.getString("followers")
                    usersData.following = jsonObject.getString("following")
                    listUsers.add(usersData)
                    listUserMutable.postValue(listUsers)
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

    fun getUserSearch(query: String, context: Context) {
        var queryUser = "https://api.github.com/search/users?q=$query"

        httpClient.addHeader(AUTH_AGENT, TOKEN)
        httpClient.addHeader(AUTH_USER, REQ_ACCESS)
        httpClient.get(queryUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(BottomNavigationActivity.TAG, result!!)

                try {
                     listUsers.clear()
                        val jsonArray = JSONObject(result)
                        val item = jsonArray.getJSONArray("items")
                        for (i in 0 until item.length()) {
                            val jsonObject = item.getJSONObject(i)
                            val username = jsonObject.getString("login")
                            getUserDetail(username, context)}

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