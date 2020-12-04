package com.amrilhs.amrilgithubuserapp3.reminder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amrilhs.amrilgithubuserapp3.R
import kotlinx.android.synthetic.main.activity_setup_reminder.*

class SetupReminder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_reminder)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Setup Reminder"

        val sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        val reminderReceiver = ReminderReceiver()
        val statusReminder = sharedPref.getBoolean(getString(R.string.status_reminder), false)
        sw_reminder.isChecked = statusReminder

        sw_reminder.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean(getString(R.string.status_reminder), isChecked)
            editor.apply()

            if (!isChecked) {
                if (reminderReceiver.isAlarmSet(this)) {
                    reminderReceiver.cancelAlarm(this)
                }
            } else {
                reminderReceiver.setRepeatingAlarm(this, ReminderReceiver.TYPE_REPEATING)
            }
        }
    }
}