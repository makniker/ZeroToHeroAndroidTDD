package ru.easycode.zerotoheroandroidtdd.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.core.SingleLiveEvent

interface ListLiveDataWrapper {
    interface Mutable: Update, Read, Save
    interface All : Add, Mutable {
        class Base: All {
            private val liveData: MutableLiveData<ArrayList<CharSequence>> = SingleLiveEvent()

            override fun liveData(): LiveData<List<CharSequence>> = liveData.map { it.toList() }

            override fun save(bundleWrapper: BundleWrapper.Save) {
                bundleWrapper.save(ArrayList(liveData.value!!))
            }

            override fun update(value: List<CharSequence>) {
                liveData.value = ArrayList(value)
            }

            override fun add(source: CharSequence) {
                val list = liveData.value?: ArrayList()
                list.add(source)
                update(list)
            }
        }
    }
    interface Save : ListLiveDataWrapper {
        fun save(bundleWrapper: BundleWrapper.Save)
    }
    interface Read : ListLiveDataWrapper {
        fun liveData(): LiveData<List<CharSequence>>
    }
    interface Update : ListLiveDataWrapper {
        fun update(value: List<CharSequence>)
    }
    interface Add : ListLiveDataWrapper {
        fun add(source: CharSequence)
    }
}
