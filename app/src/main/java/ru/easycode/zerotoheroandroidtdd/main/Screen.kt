package ru.easycode.zerotoheroandroidtdd.main

import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(supportFragmentManager: FragmentManager, container: Int)
}