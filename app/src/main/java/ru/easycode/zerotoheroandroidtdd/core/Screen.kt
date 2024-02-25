package ru.easycode.zerotoheroandroidtdd.core

import androidx.fragment.app.FragmentManager

interface Screen {
    fun show(manager: FragmentManager)
}