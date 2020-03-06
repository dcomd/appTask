package com.example.nepomucenotasks.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nepomucenotasks.data.constants.DatabaseConstantes
import com.example.nepomucenotasks.data.entities.PriorityEntity

@Dao
interface PriorityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<PriorityEntity>)

    @Query("SELECT * FROM ${DatabaseConstantes.PRIORITY.TABLE_NAME} ORDER BY ${DatabaseConstantes.PRIORITY.COLUMNS.ID}")
    fun getList(): MutableList<PriorityEntity>
}