package com.example.nepomucenotasks.data.repository

import android.app.Application
import com.example.nepomucenotasks.data.DataBaseHelper
import com.example.nepomucenotasks.data.entities.PriorityEntity
import com.example.nepomucenotasks.data.interfaces.IPriority

class PriorityRespository(application: Application) : IPriority {

    private val priorityDao = DataBaseHelper.getInstance(application)?.priorityDao()

    override fun getList(): MutableList<PriorityEntity> = priorityDao!!.getList()
}