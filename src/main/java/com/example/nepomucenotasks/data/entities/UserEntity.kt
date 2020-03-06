package com.example.nepomucenotasks.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nepomucenotasks.data.constants.DatabaseConstantes


@Entity(tableName = DatabaseConstantes.USER.TABLE_NAME)
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    var email:String,
    var name : String,
    var password: String = "")