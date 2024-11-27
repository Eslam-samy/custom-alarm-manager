package com.degel.medicationremainderapp.domain.repository

import com.degel.medicationremainderapp.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

     fun getAllReminders(): Flow<List<Reminder>>
    suspend fun insertReminder(reminder: Reminder)
    suspend fun deleteReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)


}