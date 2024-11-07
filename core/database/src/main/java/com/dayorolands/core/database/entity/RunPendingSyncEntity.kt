package com.dayorolands.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


/* Here's an entity expected to handle api sync tasks that fail to the backend.
* So on login, the `DeletedRunSyncEntity` table is first checked before the
* `RunPendingSyncEntity` */

@Entity
data class RunPendingSyncEntity(
    @Embedded val run: RunEntity,
    @PrimaryKey(autoGenerate = false)
    val runId: String = run.id,
    val mapPictureBytes: ByteArray,
    val userId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RunPendingSyncEntity

        if (run != other.run) return false
        if (runId != other.runId) return false
        if (!mapPictureBytes.contentEquals(other.mapPictureBytes)) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = run.hashCode()
        result = 31 * result + (runId?.hashCode() ?: 0)
        result = 31 * result + mapPictureBytes.contentHashCode()
        result = 31 * result + userId.hashCode()
        return result
    }
}