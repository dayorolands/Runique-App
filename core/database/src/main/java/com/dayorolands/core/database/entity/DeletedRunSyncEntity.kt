package com.dayorolands.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/* Imagine you delete a run locally from the `RunEntity` database
* and for some reason, the api call to notify the backend of the deleted run fails.
* This entity is created to handle such scenarios. */
/* So the idea is to fetch deleted runs first on login and try to notify the api once more. */
@Entity
data class DeletedRunSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val runId: String,
    val userId: String
)
