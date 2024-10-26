package com.dayorolands.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dayorolands.core.database.dao.RunDao
import com.dayorolands.core.database.dao.RunPendingSyncDao
import com.dayorolands.core.database.entity.DeletedRunSyncEntity
import com.dayorolands.core.database.entity.RunEntity
import com.dayorolands.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class],
    version = 1
)
abstract class RunDatabase: RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}