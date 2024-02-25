package ru.easycode.zerotoheroandroidtdd.list

import ru.easycode.zerotoheroandroidtdd.core.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper

interface ListLiveDataWrapper {
    interface Mutable: Update, Read {
        fun save(bundleWrapper: BundleWrapper.Save)
    }
    interface All : Add, Mutable
    class Base: LiveDataWrapper.Abstract<List<CharSequence>>(), All {
        override fun save(bundleWrapper: BundleWrapper.Save) {
            liveData.value?.let {
                bundleWrapper.save(ArrayList(it))
            }
        }

        override fun add(source: CharSequence) {
            val list = liveData.value?.toMutableList()?: ArrayList()
            list.add(source)
            update(list)
        }
    }
    interface Read : LiveDataWrapper.Read<List<CharSequence>>
    interface Update : LiveDataWrapper.Update<List<CharSequence>>
    interface Add : ListLiveDataWrapper {
        fun add(source: CharSequence)
    }
}
