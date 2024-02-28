package ru.easycode.zerotoheroandroidtdd.folder.core

import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.FoldersDao
import ru.easycode.zerotoheroandroidtdd.core.NotesDao
import ru.easycode.zerotoheroandroidtdd.note.core.Now

interface FoldersRepository {
    interface Create {
        suspend fun createFolder(name: String): Long
    }

    interface Edit {
        suspend fun delete(folderId: Long)
        suspend fun rename(folderId: Long, newName: String)
    }

    interface ReadList {
        suspend fun folders(): List<Folder>
    }

    class Base(
        private val now: Now, private val foldersDao: FoldersDao, private val notesDao: NotesDao
    ) : ReadList, Create, Edit {
        override suspend fun createFolder(name: String): Long {
            val t = now.timeInMillis()
            foldersDao.insert(FolderCache(t, name))
            return t
        }

        override suspend fun delete(folderId: Long) {
            notesDao.deleteByFolderId(folderId)
            foldersDao.delete(folderId)
        }

        override suspend fun rename(folderId: Long, newName: String) {
            foldersDao.insert(FolderCache(folderId, newName))
        }

        override suspend fun folders(): List<Folder> = foldersDao.folders().map { Folder(it.id, it.text, notesDao.notes(it.id).size) }
    }
}