package com.example.nepomucenotasks.data.dao

import androidx.room.*
import com.example.nepomucenotasks.data.constants.DatabaseConstantes
import com.example.nepomucenotasks.data.entities.TaskEntity


@Dao
interface TaskDao {

    @Query("SELECT * FROM ${DatabaseConstantes.TASK.TABLE_NAME} WHERE ${DatabaseConstantes.TASK.COLUMNS.ID} = :taskID")
    fun get(taskID: Int): TaskEntity?

    @Query("SELECT * FROM ${DatabaseConstantes.TASK.TABLE_NAME} WHERE ${DatabaseConstantes.TASK.COLUMNS.COMPLETE} = :filter AND ${DatabaseConstantes.TASK.COLUMNS.USERID} = :userId")
    fun getList(filter: Int, userId: Int): MutableList<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: TaskEntity): Long

    @Update
    fun update(task: TaskEntity)

    @Delete
    fun delete(task: TaskEntity)

}