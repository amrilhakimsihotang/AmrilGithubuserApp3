package com.amrilhs.amrilgithubuserapp3

import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amrilhs.amrilgithubuserapp3.adapter.ViewPagerAdapter
import com.amrilhs.amrilgithubuserapp3.model.UsersData
import com.amrilhs.amrilgithubuserapp3.roomdatabase.RoomMainActivity
import com.amrilhs.amrilgithubuserapp3.roomdatabase.model.UserGithub
import com.amrilhs.amrilgithubuserapp3.roomdatabase.roomviewmodel.FavoriteViewModel
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.AVATAR
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.COMPANY
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.CONTENT_URI
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FAVORITE
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FOLLOWERS
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.FOLLOWING
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.LOCATION
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.NAME
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.REPOSITORY
import com.amrilhs.amrilgithubuserapp3.sqldatabase.db.SqliteDatabase.FavoriteBaseColumns.Companion.USERNAME
import com.amrilhs.amrilgithubuserapp3.sqldatabase.helper.QueryDbHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.list_user.*


class DetailActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private lateinit var queryDbHelper: QueryDbHelper
    private var statusFavorite = false

    //room for local
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height1).toInt()
        }
        UsersData()
        viewPagerDetail()
        fab_favorite.setOnClickListener(this)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        queryDbHelper = QueryDbHelper.getInstance(applicationContext)
        queryDbHelper.open()

        val getUsername = intent.getParcelableExtra<UsersData>(EXTRA_DETAIL) as UsersData
        val cursor: Cursor = queryDbHelper.queryByUsername(getUsername.username.toString())
        if (cursor.moveToNext()) {
            statusFavorite = true
            setStatusFavorite(true)
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }

    }

    private fun setStatusFavorite(status: Boolean) {
        if (status) {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_favorite.setImageResource(R.drawable.nofavorite)
        }
    }

    private fun UsersData() {
        val usersData = intent.getParcelableExtra<UsersData>(EXTRA_DETAIL) as UsersData
        Glide.with(this)
            .load(usersData!!.avatar)
            .apply(RequestOptions().override(140, 140))
            .into(det_avatar)
        det_fullName.text = usersData.name
        det_username.text = getString(R.string.det_username, usersData.username)
        det_repository.text = getString(R.string.repository, usersData.repository)
        det_followers.text = getString(R.string.follower, usersData.followers)
        det_following.text = getString(R.string.following, usersData.following)
        det_company.text = getString(R.string.company, usersData.company)
        det_location.text = getString(R.string.location, usersData.location)
    }

    private fun viewPagerDetail() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewpager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_fav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun inputCheck(
        fullname: String,
        username: String,
        company: String,
        location: String
    ): Boolean {
        return !(TextUtils.isEmpty(fullname) && TextUtils.isEmpty(username) && TextUtils.isEmpty(
            company
        ) && TextUtils.isEmpty(location))
    }

    private fun insertDatatoDatabase() {

        val name = det_fullName.text.toString()
        val username = det_username.text.toString()
        val company = det_company.text.toString()
        val location = det_location.text.toString()

        if (inputCheck(name, username, company, location)) {
            val userGithub = UserGithub(0, name, username, company, location)
            favoriteViewModel.addUser(userGithub)
            Toast.makeText(this, R.string.alert_success_add, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.bt_adddata -> {
                insertDatatoDatabase()
            }

            R.id.bt_favorite -> {
                val mIntent = Intent(applicationContext, RoomMainActivity::class.java)
                startActivity(mIntent)
            }
            R.id.bt_share -> {

                val sMessage: String= resources.getString(R.string.share_message) + list_username.text.toString()
                val mIntent= Intent(Intent.ACTION_SEND)
                mIntent.type="text/plain"
                mIntent.putExtra(Intent.EXTRA_TEXT,sMessage)
                startActivity(Intent.createChooser(mIntent,"Share to: "))

            }
        }

    }

    override fun onClick(v: View?) {
        val usersData = intent.getParcelableExtra<UsersData>(EXTRA_DETAIL) as UsersData
        when (v?.id) {
            R.id.fab_favorite -> {
                if (statusFavorite) {
                    val idUser = usersData.username.toString()
                    queryDbHelper.deleteById(idUser)
                    Toast.makeText(
                        this,R.string.alert_delete,
                        Toast.LENGTH_SHORT
                    ).show()
                    setStatusFavorite(false)
                    statusFavorite = true
                } else {

                    val values = ContentValues()
                    values.put(USERNAME, usersData.username)
                    values.put(NAME, usersData.name)
                    values.put(AVATAR, usersData.avatar)
                    values.put(COMPANY, usersData.company)
                    values.put(LOCATION, usersData.location)
                    values.put(REPOSITORY, usersData.repository)
                    values.put(FOLLOWERS, usersData.followers)
                    values.put(FOLLOWING, usersData.following)
                    values.put(FAVORITE, "isFav")

                    statusFavorite = false
                    contentResolver.insert(CONTENT_URI, values)
                    Toast.makeText(
                        this, R.string.alert_success_add,
                        Toast.LENGTH_SHORT
                    ).show()
                    setStatusFavorite(true)
                }
            }
        }
    }

}