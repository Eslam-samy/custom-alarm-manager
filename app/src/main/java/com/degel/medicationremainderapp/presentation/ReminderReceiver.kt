package com.degel.medicationremainderapp.presentation

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.degel.medicationremainderapp.CHANNEL
import com.degel.medicationremainderapp.R
import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.usecase.UpdateReminderUseCase
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val DONE = "done"
const val REJECT = "reject"


@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var updateReminderUseCase: UpdateReminderUseCase

    private lateinit var mediaPlayer: MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {

        mediaPlayer = MediaPlayer.create(context, R.raw.alarm_music)
        val reminderJson = intent.getStringExtra(REMINDER)
        val reminder = Gson().fromJson(reminderJson, Reminder::class.java)

        val doneIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, reminderJson)
            action = DONE
        }

        val donePendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.timeInMillis.toInt(),
            doneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val closeIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, reminderJson)
            action = REJECT
        }
        val closePendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.timeInMillis.toInt(),
            closeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.d("ESLAMDEGEL", "onReceive: " + intent.action)
        when (intent.action) {
            DONE -> {
                runBlocking {
                    updateReminderUseCase(reminder.copy(isTaken = true))
                    cancelAlarm(context, reminder)
                    mediaPlayer.stop()
                    NotificationManagerCompat.from(context).cancel(1)
                }
            }

            REJECT -> {
                runBlocking {
                    updateReminderUseCase(reminder.copy(isTaken = false))
                    cancelAlarm(context, reminder)
                    mediaPlayer.stop()
                    NotificationManagerCompat.from(context).cancel(1)

                }
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context, android.Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val notification = NotificationCompat.Builder(context, CHANNEL)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentText("SetupMedication reminder")
                            .setContentText(reminder.name.plus(reminder.dosage))
                            .addAction(R.drawable.ic_check, "Done", donePendingIntent)
                            .addAction(R.drawable.ic_close, "Close", closePendingIntent).build()

                        NotificationManagerCompat.from(context).notify(1, notification)
                    }
                } else {
                    val notification = NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText("SetupMedication reminder")
                        .setContentText(reminder.name.plus(reminder.dosage))
                        .addAction(R.drawable.ic_check, "Done", donePendingIntent)
                        .addAction(R.drawable.ic_close, "Close", closePendingIntent).build()

                    NotificationManagerCompat.from(context).notify(1, notification)
                }
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
                mediaPlayer.start()

            }

        }

    }
}