package com.example.nepomucenotasks.data.constants

class DatabaseConstantes {

    object DATABASENAME{
        const val DATABASE_NAME = "TASKDATABASE"
    }

    object USER {
        const val TABLE_NAME = "user"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val EMAIL = "email"
            const val PASSWORD = "password"
        }
    }

    object PRIORITY {
        const val TABLE_NAME = "priority"

        object COLUMNS {
            const val ID = "id"
            const val DESCRIPTION = "description"
        }

    }

    object TASK {
        const val TABLE_NAME = "task"

        object COLUMNS {
            const val ID = "id"
            const val USERID = "userid"
            const val DESCRIPTION = "description"
            const val DUEDATE = "duedate"
            const val PRIORITYID = "priorityid"
            const val COMPLETE = "complete"
        }

    }
}