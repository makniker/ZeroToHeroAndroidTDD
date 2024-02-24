package ru.easycode.zerotoheroandroidtdd.core

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

interface BundleWrapper {
    interface Mutable : Save, Restore {
        class Base(private val bundle: Bundle) : Mutable {
            override fun save(list: ArrayList<CharSequence>) {
                bundle.putCharSequenceArrayList("key", list)
            }

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            override fun restore(): ArrayList<CharSequence> {
                return bundle.getCharSequenceArrayList("key") ?: ArrayList()
            }
        }
    }

    interface Save {
        fun save(list: ArrayList<CharSequence>)
    }

    interface Restore {
        fun restore(): List<CharSequence>
    }
}