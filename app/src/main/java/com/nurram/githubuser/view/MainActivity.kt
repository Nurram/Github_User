package com.nurram.githubuser.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.usersapp.R
import com.nurram.githubuser.DailyReminderReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var dailyReminderReceiver: DailyReminderReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f



        dailyReminderReceiver = DailyReminderReceiver()

        Log.d("TAG", "daily status ${SettingsActivity.DAILY_REMINDER_STATUS}")
        if (SettingsActivity.DAILY_REMINDER_STATUS) {
            dailyReminderReceiver.setAlarm(this, DailyReminderReceiver.DAILY_BROADCAST_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.favourite -> {
                val intent = Intent(this, FavouriteActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }
}
