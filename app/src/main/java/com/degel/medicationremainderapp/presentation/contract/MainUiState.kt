package com.degel.medicationremainderapp.presentation.contract

import com.degel.medicationremainderapp.domain.model.Reminder

data class MainUiState(
    val reminders: List<Reminder> = emptyList(),
)
