package com.amrilhs.amrilgithubuserapp3

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.adapter.FavoriteUserAdapter
import com.amrilhs.amrilgithubuserapp3.model.UserFavorite
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.CONTENT_URI
import com.amrilhs.amrilgithubuserapp3.sqldatabase.helper.MapDbHelper
import com.amrilhs.amrilgithubuserapp3.sqldatabase.helper.QueryDbHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class UserFavoriteActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }


    private lateinit var favoriteUserAdapter: FavoriteUserAdapter
    private lateinit var queryDbHelper: QueryDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_favorite)

        queryDbHelper= QueryDbHelper.getInstance(applicationContext)
        queryDbHelper.open()

        rc_userFavorite.layoutManager=LinearLayoutManager(this)
        rc_userFavorite.setHasFixedSize(true)
        favoriteUserAdapter = FavoriteUserAdapter(this)
        rc_userFavorite.adapter = favoriteUserAdapter

        val handleThread = HandlerThread("DataObserver")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadListUserFavorite()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadListUserFavorite()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFavorite>(EXTRA_STATE)
            if (list != null) {
                favoriteUserAdapter.listUserFavorite = list
            }
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Favorite User"
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteUserAdapter.listUserFavorite)
    }

    private fun loadListUserFavorite() {
        GlobalScope.launch(Dispatchers.Main) {
            pbMainActivity.visibility = View.VISIBLE
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MapDbHelper.mapDbHelperCursorToArrayList(cursor)
            }
            val userFavorit = deferredFav.await()
            pbMainActivity.visibility = View.INVISIBLE
            if (userFavorit.size > 0) {
                favoriteUserAdapter.listUserFavorite = userFavorit
            } else {
                favoriteUserAdapter.listUserFavorite= ArrayList()
                showSnack()
            }
        }
    }

    private fun showSnack() {
        Snackbar.make(rc_userFavorite, "No data record", Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadListUserFavorite()
    }
}