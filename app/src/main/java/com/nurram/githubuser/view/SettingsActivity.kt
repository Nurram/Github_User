package com.nurram.githubuser.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.usersapp.R
import com.nurram.githubuser.DailyReminderReceiver

class SettingsActivity : AppCompatActivity() {

    companion object {
        var DAILY_REMINDER_STATUS = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private var dailyReminderReceiver: DailyReminderReceiver = DailyReminderReceiver()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)


        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onStop() {
            super.onStop()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            var status = true
            if (sharedPreferences != null) {
                status =
                    sharedPreferences.getBoolean(getString(R.string.setting_daily), true)
                DAILY_REMINDER_STATUS = status
            }

            dailyReminderReceiver.changeAlarmSetting(
                context,
                DailyReminderReceiver.DAILY_BROADCAST_ID,
                status
            )
            Log.d(
                "TAG",
                "Alarm Setting Changed, ${DailyReminderReceiver.DAILY_BROADCAST_ID} $status"
            )
        }
    }
}