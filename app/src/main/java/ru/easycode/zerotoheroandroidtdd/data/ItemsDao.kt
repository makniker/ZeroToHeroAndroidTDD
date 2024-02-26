package ru.easycode.zerotoheroandroidtdd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {
    @Query("SELECT * FROM item")
    fun list(): List<ItemCache>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: ItemCache)

    @Query("SELECT * FROM item WHERE id= :id LIMIT 1")
    fun item(id: Long): ItemCache
    @Query("DELETE FROM item WHERE id=:id")
    fun delete(id: Long)
}