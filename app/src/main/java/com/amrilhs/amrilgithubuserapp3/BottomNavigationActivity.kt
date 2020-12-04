package com.amrilhs.amrilgithubuserapp3

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amrilhs.amrilgithubuserapp3.reminder.SetupReminder
import com.amrilhs.amrilgithubuserapp3.web.WebDicodingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationActivity : AppCompatActivity() {
    companion object {
        val TAG = BottomNavigationActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_bottom)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,R.id.navigation_favorite))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }
    private fun showDialogAbout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_about)
        builder.setMessage(R.string.content_about)
        builder.setPositiveButton("OK", { _,_ ->})
        builder.show()
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.btn_favorite -> {
                val mIntent = Intent(this,UserFavoriteActivity::class.java)
                startActivity(mIntent)
            }
            R.id.change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.bt_dicoding_web ->{
                val mWebIntent= Intent(this, WebDicodingActivity::class.java)
                startActivity(mWebIntent)
            }
            R.id.bt_reminder ->{
                val mReminderIntent= Intent(this,SetupReminder::class.java)
                startActivity(mReminderIntent)
            }
            R.id.bt_about ->{
                showDialogAbout()
            }

            R.id.exit_btn -> {
                finishAffinity()
            }
        }

    }
}
