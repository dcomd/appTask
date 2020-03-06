package com.example.nepomucenotasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nepomucenotasks.data.constants.DatabaseConstantes
import com.example.nepomucenotasks.data.dao.PriorityDao
import com.example.nepomucenotasks.data.dao.TaskDao
import com.example.nepomucenotasks.data.dao.UserDao
import com.example.nepomucenotasks.data.entities.PriorityEntity
import com.example.nepomucenotasks.data.entities.TaskEntity
import com.example.nepomucenotasks.data.entities.UserEntity
import java.util.concurrent.Executors


@Database(entities = [UserEntity::class, PriorityEntity::class, TaskEntity::class], version = 1)
abstract class DataBaseHelper : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun priorityDao(): PriorityDao
    abstract fun taskDao(): TaskDao


    companion object {
        private var INSTANCE: DataBaseHelper? = null

        var listPriority = listOf(
            PriorityEntity(1, "Baixa"),
            PriorityEntity(2, "Média"),
            PriorityEntity(3, "Alta"),
            PriorityEntity(4, "Crítica")
        )

        fun getInstance(context: Context): DataBaseHelper? {
            if (INSTANCE == null) {
                synchronized(DataBaseHelper::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseHelper::class.java, DatabaseConstantes.DATABASENAME.DATABASE_NAME
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadExecutor().execute {
                                    INSTANCE?.let {
                                        it.priorityDao().insert(listPriority)
                                    }

                                }
                            }
                        })
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}