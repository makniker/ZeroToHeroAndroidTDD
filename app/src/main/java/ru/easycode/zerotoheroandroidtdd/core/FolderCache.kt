package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "folders")
data class FolderCache(@PrimaryKey(autoGenerate = true) val id: Long, @ColumnInfo("text") val text: String)
