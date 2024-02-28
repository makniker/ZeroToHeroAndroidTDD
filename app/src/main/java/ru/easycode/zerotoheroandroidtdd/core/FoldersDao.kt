package ru.easycode.zerotoheroandroidtdd.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoldersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: FolderCache)
    @Query("DELETE FROM folders WHERE id=:folderId")
    suspend fun delete(folderId: Long)
    @Query("SELECT * FROM folders")
    suspend fun folders(): List<FolderCache>
}
