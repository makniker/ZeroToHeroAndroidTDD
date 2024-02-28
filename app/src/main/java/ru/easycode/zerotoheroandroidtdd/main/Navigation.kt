package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper

interface Navigation {
    interface Mutable: Read, Update
    interface Read : LiveDataWrapper.Read<Screen>
    interface Update {
        fun update(screen: Screen)
    }

    class Base: Mutable {
        val liveData: LiveData<Screen> = MutableLiveData()
        override fun liveData(): LiveData<Screen> = liveData
        override fun update(screen: Screen) {
            TODO()
        }
    }
}