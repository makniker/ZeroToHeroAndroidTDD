package ru.easycode.zerotoheroandroidtdd.folder.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi

class FolderLiveDataWrapper {

    interface Read: LiveDataWrapper.Read<FolderUi>
    interface Rename: Read {
        fun rename(newName: String)
    }

    interface Update: Read {
        fun update(folder: FolderUi)
    }

    interface Mutable : Update {
        fun folderId(): Long
    }
    interface Increment: Read {
        fun increment()
    }

    interface Decrement: Read {
        fun decrement()
    }

    class Base(private val liveData: MutableLiveData<FolderUi> = MutableLiveData()): Rename, Mutable, Increment, Decrement {
        override fun rename(newName: String) {
            liveData.value?.title = newName
        }

        override fun folderId(): Long = liveData.value?.id!!

        override fun update(folder: FolderUi) {
            liveData.value = folder
        }

        override fun increment() {
            liveData.value?.notesCount = liveData.value?.notesCount!! - 1
        }

        override fun decrement() {
            liveData.value?.notesCount = liveData.value?.notesCount!! - 1
        }

        override fun liveData(): LiveData<FolderUi> = liveData

    }
}
