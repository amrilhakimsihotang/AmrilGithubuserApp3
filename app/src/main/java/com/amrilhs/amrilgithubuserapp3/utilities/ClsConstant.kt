package com.amrilhs.amrilgithubuserapp3.utilities

import com.loopj.android.http.AsyncHttpClient

/*Display 10 users for page 1*/
const val BASE_URL = "https://api.github.com/users?per_page=10&page=1"

/*Token, auth agent,auth user*/
const val TOKEN = "token c15b3b82e4987c54f3ab84b61d55980599470d7c"

const val AUTH_AGENT = "Authorization"
const val AUTH_USER = "User-Agent"
const val REQ_ACCESS = "request"
val httpClient = AsyncHttpClient()