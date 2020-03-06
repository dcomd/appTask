package com.example.nepomucenotasks.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nepomucenotasks.data.constants.DatabaseConstantes
import com.example.nepomucenotasks.data.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM ${DatabaseConstantes.USER.TABLE_NAME} WHERE ${DatabaseConstantes.USER.COLUMNS.EMAIL} = :email AND ${DatabaseConstantes.USER.COLUMNS.PASSWORD} = :password")
    fun get(email: String, password: String): UserEntity?

    @Query("SELECT * FROM ${DatabaseConstantes.USER.TABLE_NAME} WHERE ${DatabaseConstantes.USER.COLUMNS.EMAIL} = :email")
    fun isEmailExistent(email: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity): Long
}