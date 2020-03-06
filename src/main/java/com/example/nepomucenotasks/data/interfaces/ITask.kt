package com.example.nepomucenotasks.data.interfaces

import com.example.nepomucenotasks.data.entities.TaskEntity

interface ITask {

    fun get(taskID: Int): TaskEntity?

    fun getList(filter: Int, userId: Int): MutableList<TaskEntity>

    fun insert(task: TaskEntity): Long

    fun update(task: TaskEntity)

    fun delete(task: TaskEntity)
}