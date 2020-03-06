package com.example.nepomucenotasks.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nepomucenotasks.data.constants.DatabaseConstantes



@Entity(tableName = DatabaseConstantes.TASK.TABLE_NAME)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val userId: Long,
    var priorityId: Long = 0,
    var description: String,
    var dueDate: String,
    var complete: Boolean = false
)
