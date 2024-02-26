package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemCache::class], version = 1, exportSchema = false)
abstract class ItemsDataBase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao

    companion object {
        @Volatile
        private var INSTANCE: ItemsDataBase? = null
        fun getDatabase(context: Context): ItemsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDataBase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}