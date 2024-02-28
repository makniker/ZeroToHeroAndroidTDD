package ru.easycode.zerotoheroandroidtdd.note.core

import ru.easycode.zerotoheroandroidtdd.core.NoteCache
import ru.easycode.zerotoheroandroidtdd.core.NotesDao

interface NotesRepository {
    interface ReadList {
        suspend fun noteList(folderId: Long): List<MyNote>
    }

    interface Create {
        suspend fun createNote(folderId: Long, text: String): Long
    }

    interface Edit {
        suspend fun renameNote(noteId: Long, newName: String)
        suspend fun note(noteId: Long): MyNote
        suspend fun deleteNote(noteId: Long)
    }

    class Base(
        private val now: Now, private val dao: NotesDao
    ): ReadList, Create, Edit {
        override suspend fun noteList(folderId: Long): List<MyNote> = dao.notes(folderId).map { MyNote(it.id, it.text, it.folderId) }

        override suspend fun createNote(folderId: Long, text: String): Long {
            val id = now.timeInMillis()
            dao.insert(NoteCache(id, text, folderId))
            return id
        }

        override suspend fun renameNote(noteId: Long, newName: String) {
            val note = dao.note(noteId)
            dao.insert(NoteCache(noteId, newName, note.folderId))
        }

        override suspend fun note(noteId: Long): MyNote {
            val note = dao.note(noteId)
            return MyNote(note.id, note.text, note.folderId)
        }

        override suspend fun deleteNote(noteId: Long) {
            dao.delete(noteId)
        }
    }
}
