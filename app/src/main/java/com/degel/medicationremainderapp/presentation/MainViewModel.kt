package com.degel.medicationremainderapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.degel.medicationremainderapp.domain.model.Reminder
import com.degel.medicationremainderapp.domain.usecase.DeleteReminderUseCase
import com.degel.medicationremainderapp.domain.usecase.GetRemindersUseCase
import com.degel.medicationremainderapp.domain.usecase.InsertReminderUseCase
import com.degel.medicationremainderapp.domain.usecase.UpdateReminderUseCase
import com.degel.medicationremainderapp.presentation.contract.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val insertReminderUseCase: InsertReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
) : ViewModel() {
    val uiState = getRemindersUseCase().map { MainUiState(it) }
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, MainUiState())

    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch {
            insertReminderUseCase(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            deleteReminderUseCase(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            updateReminderUseCase(reminder)
        }
    }


}