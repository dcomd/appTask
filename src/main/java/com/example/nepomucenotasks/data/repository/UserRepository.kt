package com.example.nepomucenotasks.data.repository

import android.app.Application
import com.example.nepomucenotasks.data.DataBaseHelper
import com.example.nepomucenotasks.data.entities.UserEntity
import com.example.nepomucenotasks.data.interfaces.IUser

class UserRepository(application: Application): IUser  {


    private val userDao = DataBaseHelper.getInstance(application)?.userDao()

    override fun get(email: String, password: String): UserEntity? = userDao?.get(email, password)

    override fun isEmailExistent(email: String): Boolean = userDao!!.isEmailExistent(email)

    override fun insert(userEntity: UserEntity): Long =
        userDao!!.insert(userEntity)

}