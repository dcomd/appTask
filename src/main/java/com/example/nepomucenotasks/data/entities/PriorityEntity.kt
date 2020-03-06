package com.example.nepomucenotasks.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nepomucenotasks.data.constants.DatabaseConstantes


@Entity(tableName = DatabaseConstantes.PRIORITY.TABLE_NAME)
data class PriorityEntity (
    @PrimaryKey(autoGenerate = false)
    val id : Long,
    val description : String )