package com.amrilhs.amrilgithubuserapp3.roomdatabase

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amrilhs.amrilgithubuserapp3.R
import com.amrilhs.amrilgithubuserapp3.roomdatabase.roomviewmodel.FavoriteViewModel

class RoomMainActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_main)

        favoriteViewModel = ViewModelProvider(
            this
        )
            .get(FavoriteViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(itemId: Int) {
        when (itemId) {
            R.id.bt_delete -> {
                val builder = AlertDialog.Builder(this)
                builder.setPositiveButton("Yes") { _, _ ->
                    favoriteViewModel.deleteAllUser()
                    Toast.makeText(
                        this, R.string.alert_delete,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle(R.string.alert_delete_title)
                builder.setMessage(R.string.alert_delete_confirm)
                builder.create().show()
            }
        }

    }

}
