package ru.easycode.zerotoheroandroidtdd.create

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.core.Navigation
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper

class CreateViewModel(private val addLiveDataWrapper: ListLiveDataWrapper.Add,
                      private val navigation: Navigation.Update,
                      private val clearViewModel: ClearViewModel
): ViewModel() {
    fun comeback() {
        clearViewModel.clear(CreateViewModel::class.java)
        navigation.update(Pop)
    }
    fun add(text: CharSequence) {
        addLiveDataWrapper.add(text)
        comeback()
    }
}
