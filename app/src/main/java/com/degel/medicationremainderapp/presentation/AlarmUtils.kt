package com.degel.medicationremainderapp.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.degel.medicationremainderapp.domain.model.Reminder
import com.google.gson.Gson

const val REMINDER = "REMINDER"
fun setupAlarm(context: Context, reminder: Reminder) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("REMINDER", Gson().toJson(reminder))
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.timeInMillis.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(
        Context.ALARM_SERVICE
    ) as AlarmManager

    try {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.timeInMillis, pendingIntent)
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

fun cancelAlarm(context: Context, reminder: Reminder) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("REMINDER", Gson().toJson(reminder))
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.timeInMillis.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(
        Context.ALARM_SERVICE
    ) as AlarmManager

    alarmManager.cancel(
        pendingIntent
    )
}

fun setupPeriodicAlarm(context: Context, reminder: Reminder) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("REMINDER", Gson().toJson(reminder))
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.timeInMillis.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(
        Context.ALARM_SERVICE
    ) as AlarmManager

    val interval = 2L * 60L * 1000L
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        reminder.timeInMillis,
        interval,
        pendingIntent
    )
}
