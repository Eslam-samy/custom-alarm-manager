package com.degel.medicationremainderapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.degel.medicationremainderapp.domain.model.Reminder

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        Reminder::class
    ]
)
abstract class ReminderDataBase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): ReminderDataBase =
            Room.databaseBuilder(context, ReminderDataBase::class.java, "reminder_db").build()
    }

    abstract fun reminderDao(): ReminderDao
}