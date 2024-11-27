package com.degel.medicationremainderapp.domain.usecase

import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,

    ) {
    suspend operator fun invoke(reminder: Reminder) {
        reminderRepository.deleteReminder(reminder)
    }
}