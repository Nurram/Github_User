package com.nurram.githubuser

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.usersapp.R
import com.nurram.githubuser.view.MainActivity
import java.util.*


class DailyReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val DAILY_BROADCAST_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(
            context,
            context.getString(R.string.comeback),
            context.getString(R.string.check_user),
            DAILY_BROADCAST_ID
        )
    }

    private fun showNotification(context: Context, title: String, msg: String, id: Int) {
        val CHANNEL_ID = "channel"
        val CHANNEL_NAME = "my channel"

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notification =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_favorite_24dp)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            builder.setAutoCancel(true)

            notification.createNotificationChannel(channel)
            notification.notify(id, builder.build())
        }
    }

    fun setAlarm(context: Context, id: Int) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)
        intent.putExtra(EXTRA_ID, id)

        val calendar = Calendar.getInstance()
        val pendingIntent: PendingIntent?

        val sharedPref = context.getSharedPreferences("alarmPref", MODE_PRIVATE)
        val isAlarmSet = sharedPref.getBoolean("isAlarmSet", false)
        Log.d("TAG", isAlarmSet.toString())

        if (!isAlarmSet) {
            Log.d("TAG", "Daily Reminder Alarm Initialized")
            calendar.set(Calendar.HOUR_OF_DAY, 9)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_BROADCAST_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            Log.d("TAG", "Alarm Initialized")
            manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

            sharedPref.edit {
                putBoolean("isAlarmSet", true)
            }
        }
    }

    private fun cancelAlarm(context: Context, id: Int) {
        Log.d("TAG", "Alarm id $id canceled")
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_NO_CREATE)

        if (pendingIntent != null) {
            manager.cancel(pendingIntent)
        }
    }

    fun changeAlarmSetting(context: Context?, id: Int, status: Boolean) {
        Log.d("TAG", "Alarm Setting Changed, $id $status")
        if (status) {
            context?.let { setAlarm(it, id) }
        } else {
            context?.let { cancelAlarm(it, id) }
        }
    }
}