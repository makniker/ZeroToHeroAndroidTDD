package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper

interface NoteLiveDataWrapper: LiveDataWrapper.Read<String> {

    fun update(noteText: String)

    class Base(private val liveData: MutableLiveData<String> = MutableLiveData()) : NoteLiveDataWrapper {
        override fun update(noteText: String) {
            liveData.value = noteText
        }

        override fun liveData(): LiveData<String> = liveData

    }
}
