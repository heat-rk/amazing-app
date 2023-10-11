package ru.heatalways.amazingapplication.core.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HateTopEntity.TABLE_NAME)
data class HateTopEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_AVATAR_PATH)
    val avatarPath: String,

    @ColumnInfo(name = COLUMN_TAP_COUNT)
    val tapCount: Int,
) {
    companion object {
        const val TABLE_NAME = "hate_top"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AVATAR_PATH = "avatar_path"
        const val COLUMN_TAP_COUNT = "tap_count"
    }
}
