package com.degel.medicationremainderapp.data.di

import android.content.Context
import androidx.room.Room
import com.degel.medicationremainderapp.data.local.ReminderDao
import com.degel.medicationremainderapp.data.local.ReminderDataBase
import com.degel.medicationremainderapp.data.repository.ReminderRepositoryImpl
import com.degel.medicationremainderapp.domain.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext app: Context): ReminderDataBase {
        return ReminderDataBase.getInstance(app)

    }

    @Singleton
    @Provides
    fun provideReminderDao(dataBase: ReminderDataBase) = dataBase.reminderDao()

    @Provides
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository {
        return ReminderRepositoryImpl(reminderDao)
    }


}