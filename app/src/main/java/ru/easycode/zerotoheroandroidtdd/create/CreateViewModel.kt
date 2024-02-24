package ru.easycode.zerotoheroandroidtdd.create

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.core.Screen
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper

class CreateViewModel(private val addLiveDataWrapper: ListLiveDataWrapper.Add,
                      private val navigation: Navigation.Update,
                      private val clearViewModel: ClearViewModel
): ViewModel() {
    fun comeback() {
        clearViewModel.clear(CreateViewModel::class.java)
        navigation.update(Screen.Pop)
    }
    fun add(text: String) {
        addLiveDataWrapper.add(text)
        comeback()
    }
}
