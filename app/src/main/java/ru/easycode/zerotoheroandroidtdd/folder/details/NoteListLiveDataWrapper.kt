package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper

class NoteListLiveDataWrapper {
    interface Read: LiveDataWrapper.Read<List<NoteUi>>
    interface Create {
        fun create(noteUi: NoteUi)
    }
    interface Update {
        fun update(noteId: Long, newText: String)
    }
    interface UpdateListAndRead: Read {
        fun update(notes: List<NoteUi>)
    }
    class Base(private val liveData: MutableLiveData<List<NoteUi>> = MutableLiveData()): Update, Create, UpdateListAndRead {
        override fun create(noteUi: NoteUi) {
            val list = liveData.value?.let { ArrayList(it) } ?: mutableListOf()
            list.add(noteUi)
            update(list)
        }

        override fun update(noteId: Long, newText: String) {
            liveData.value?.let {
                val list = liveData.value!!.toMutableList()
                val n = list.find { it.id == noteId }
                n?.let {
                    list.add(NoteUi(n.id, newText, n.folderId))
                }
                update(list)
            }
        }

        override fun update(notes: List<NoteUi>) {
            liveData.value = notes
        }

        override fun liveData(): LiveData<List<NoteUi>> = liveData

    }
}
