package com.example.nepomucenotasks.data.interfaces

import com.example.nepomucenotasks.data.entities.UserEntity

interface IUser {

    fun get(email: String, password: String): UserEntity?

    fun isEmailExistent(email: String): Boolean

    fun insert(userEntity: UserEntity): Long
}