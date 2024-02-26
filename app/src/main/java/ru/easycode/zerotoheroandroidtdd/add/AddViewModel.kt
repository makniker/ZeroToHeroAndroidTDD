package ru.easycode.zerotoheroandroidtdd.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.Repository
import ru.easycode.zerotoheroandroidtdd.core.ListLiveDataWrapper

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher= Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    fun comeback() {
        clear.clear(AddViewModel::class.java)
    }

    fun add(value: String) {
        viewModelScope.launch(dispatcher) {
            repository.add(value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(value)
            }
            comeback()
        }
    }
}
