package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData

interface LiveDataWrapper {
    interface Read<T : Any> {
        fun liveData(): LiveData<T>
    }
}