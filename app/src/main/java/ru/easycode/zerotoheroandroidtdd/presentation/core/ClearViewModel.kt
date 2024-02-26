package ru.easycode.zerotoheroandroidtdd.presentation.core

import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun clearViewModel(clasz: Class<out ViewModel>)
}
