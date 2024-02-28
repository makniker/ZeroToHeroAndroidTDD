package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "notes")
data class NoteCache(@PrimaryKey(autoGenerate = true) val id: Long, @ColumnInfo("text") val text: String, val folderId: Long)
