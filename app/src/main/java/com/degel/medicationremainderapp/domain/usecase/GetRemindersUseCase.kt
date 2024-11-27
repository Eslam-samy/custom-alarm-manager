package com.degel.medicationremainderapp.domain.usecase

import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository,
) {
     operator fun invoke(): Flow<List<Reminder>> {
        return reminderRepository.getAllReminders()
    }

}