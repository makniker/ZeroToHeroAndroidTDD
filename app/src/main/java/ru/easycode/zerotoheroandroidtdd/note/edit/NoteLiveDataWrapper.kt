package ru.easycode.zerotoheroandroidtdd.note.edit

import androidx.lifecycle.MutableLiveData

interface NoteLiveDataWrapper {
    fun update(noteText: String)

    class Base(private val liveData: MutableLiveData<String> = MutableLiveData()) : NoteLiveDataWrapper {
        override fun update(noteText: String) {
            liveData.value = noteText
        }

    }
}
