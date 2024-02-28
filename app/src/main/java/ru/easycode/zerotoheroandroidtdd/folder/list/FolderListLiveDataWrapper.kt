package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper

interface FolderListLiveDataWrapper {
    interface Read: LiveDataWrapper.Read<List<FolderUi>>
    interface Create {
        fun create(folderUi: FolderUi)
    }
    interface UpdateListAndRead: Read {
        fun update(list: List<FolderUi>)
    }

    class Base(private val liveData: MutableLiveData<List<FolderUi>> = MutableLiveData()): Create, UpdateListAndRead {
        override fun create(folderUi: FolderUi) {
            liveData.value?.let {
                val list = liveData.value!!.toMutableList()
                list.add(folderUi)
                update(list)
            }
        }

        override fun update(list: List<FolderUi>) {
            liveData.value = list
        }

        override fun liveData(): LiveData<List<FolderUi>> = liveData()

    }
}
