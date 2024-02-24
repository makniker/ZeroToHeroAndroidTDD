package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData

interface Navigation {
    interface Read: Navigation {
        fun liveData(): LiveData<Screen>
    }
    interface Update: Navigation {
        fun update(value: Screen)
    }

    interface Mutable: Read, Update {
        class Base: Mutable {
            private val mutableLiveData: SingleLiveEvent<Screen> = SingleLiveEvent()
            override fun liveData(): LiveData<Screen> {
                return mutableLiveData
            }
            override fun update(value: Screen) {
                mutableLiveData.value = value
            }
        }
    }
}
