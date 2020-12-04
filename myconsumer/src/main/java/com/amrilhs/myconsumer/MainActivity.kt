package com.amrilhs.myconsumer

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.CONTENT_URI
import com.amrilhs.amrilgithubuserapp3.sqldatabase.helper.MapDbHelper
import com.amrilhs.myconsumer.adapter.FavoriteUserAdapter
import com.amrilhs.myconsumer.model.UserFavorite
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var favoriteUserAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rc_myconsumer.layoutManager = LinearLayoutManager(this)
        rc_myconsumer.setHasFixedSize(true)
        favoriteUserAdapter = FavoriteUserAdapter(this)
        rc_myconsumer.adapter = favoriteUserAdapter

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
                favoriteUserAdapter.listUserFavorite = ArrayList()
                showSnack()
            }
        }
    }

    private fun showSnack() {
        Snackbar.make(rc_myconsumer, R.string.alert , Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteUserAdapter.listUserFavorite)
    }

    override fun onResume() {
        super.onResume()
        loadListUserFavorite()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {

            R.id.bt_exit2 -> {
                finishAffinity()
            }
        }

    }
}