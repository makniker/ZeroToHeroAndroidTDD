package ru.easycode.zerotoheroandroidtdd.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [NoteCache::class, FolderCache::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun foldersDao(): FoldersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
