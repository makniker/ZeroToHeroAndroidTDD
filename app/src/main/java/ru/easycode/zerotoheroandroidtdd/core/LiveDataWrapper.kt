package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {
    interface Read<T : Any> : LiveDataWrapper {
        fun liveData(): LiveData<List<T>>
    }
    interface Update<T : Any> : LiveDataWrapper {
        fun update(value: List<T>)
    }
    interface Add<T : Any> : LiveDataWrapper {
        fun add(value: T)
    }
    interface Mutable<T : Any> : Read<T>, Update<T>
    abstract class Abstract<T : Any>(protected val liveData: MutableLiveData<List<T>> = MutableLiveData()) :
        Mutable<T>, Add<T> {
        override fun liveData(): LiveData<List<T>> = liveData
        override fun update(value: List<T>) {
            liveData.value = value
        }
        override fun add(value: T) {
            liveData.value?.let {
                val list = liveData.value!!.toMutableList()
                list.add(value)
                update(list)
            }
        }

    }
}