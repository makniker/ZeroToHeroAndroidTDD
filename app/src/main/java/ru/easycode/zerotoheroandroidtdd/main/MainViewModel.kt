package ru.easycode.zerotoheroandroidtdd.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen

class MainViewModel(private val navigation: Navigation.Mutable): ViewModel(), LiveDataWrapper.Read<Screen> {
    fun init(firstRun: Boolean) {
        if (firstRun) {
            navigation.update(FoldersListScreen)
        }
    }

    override fun liveData(): LiveData<Screen> = navigation.liveData()
}
