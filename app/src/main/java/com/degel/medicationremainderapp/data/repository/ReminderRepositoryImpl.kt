package com.degel.medicationremainderapp.data.repository

import com.degel.medicationremainderapp.data.local.ReminderDao
import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderRepository {
    override  fun getAllReminders(): Flow<List<Reminder>> {
        return reminderDao.getAllReminders()
    }

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }
}