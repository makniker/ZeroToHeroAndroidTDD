package ru.easycode.zerotoheroandroidtdd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="item")
data class ItemCache(@PrimaryKey(autoGenerate = true) val id: Long, @ColumnInfo(name = "text") val text: String)
