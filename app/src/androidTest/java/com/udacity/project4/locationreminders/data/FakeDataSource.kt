package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {
    private var shouldReturnError = false
    private var reminderServiceData: LinkedHashMap<String, ReminderDTO> = LinkedHashMap()

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if (shouldReturnError) {
            return Result.Error("Test exception")
        }
        return Result.Success(reminderServiceData.values.toList())
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminderServiceData[reminder.id] = reminder

    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if (shouldReturnError) {
            return Result.Error("Test exception")
        }
        reminderServiceData[id]?.let {
            return Result.Success(it)
        }
        return Result.Error("Could not find Reminder")
    }

    override suspend fun deleteAllReminders() {
        reminderServiceData.clear()
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

}