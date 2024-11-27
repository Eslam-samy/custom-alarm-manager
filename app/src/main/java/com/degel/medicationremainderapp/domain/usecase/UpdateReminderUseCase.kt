package com.degel.medicationremainderapp.domain.usecase

import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.updateReminder(reminder)
    }
}