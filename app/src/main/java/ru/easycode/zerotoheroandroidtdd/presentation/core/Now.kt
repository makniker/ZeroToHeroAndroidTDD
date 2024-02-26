package ru.easycode.zerotoheroandroidtdd.presentation.core

interface Now {
    fun nowMillis(): Long
    class Base: Now {
        override fun nowMillis(): Long = System.currentTimeMillis()
    }
}