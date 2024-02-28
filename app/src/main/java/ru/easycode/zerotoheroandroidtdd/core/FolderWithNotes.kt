package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Embedded
import androidx.room.Relation


data class FolderWithNotes(
    @Embedded val folderCache: FolderCache,
    @Relation(parentColumn = "id", entityColumn = "folderId") val notes: List<NoteCache>
)