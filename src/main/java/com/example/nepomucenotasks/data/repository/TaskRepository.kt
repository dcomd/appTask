package com.example.nepomucenotasks.data.repository

import android.app.Application
import com.example.nepomucenotasks.data.DataBaseHelper
import com.example.nepomucenotasks.data.entities.TaskEntity
import com.example.nepomucenotasks.data.interfaces.ITask

class TaskRepository(application: Application) : ITask {

    private val taskDao = DataBaseHelper.getInstance(application)?.taskDao()

    override fun get(taskID: Int): TaskEntity? = taskDao!!.get(taskID)

    override fun getList(filter: Int, userId: Int): MutableList<TaskEntity> =
            taskDao!!.getList(filter, userId)

    override fun insert(task: TaskEntity): Long = taskDao!!.insert(task)

    override fun update(task: TaskEntity) = taskDao!!.update(task)

    override fun delete(task: TaskEntity) = taskDao!!.delete(task)

}