package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.MutableLiveData

class NoteListLiveDataWrapper {
    interface Create {
        fun create(noteUi: NoteUi)
    }
    interface Update {
        fun update(noteId: Long, newText: String)
    }
    interface UpdateListAndRead {
        fun update(notes: List<NoteUi>)
    }
    class Base(private val liveData: MutableLiveData<List<NoteUi>> = MutableLiveData()): Update, Create, UpdateListAndRead {
        override fun create(noteUi: NoteUi) {
            liveData.value?.let {
                val list = liveData.value!!.toMutableList()
                list.add(noteUi)
                update(list)
            }
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

    }
}
