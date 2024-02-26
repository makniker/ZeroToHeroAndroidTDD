package ru.easycode.zerotoheroandroidtdd.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.presentation.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi
import ru.easycode.zerotoheroandroidtdd.presentation.core.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.data.Repository

class AddViewModel(
    private val repository: Repository.Add,
    private val liveDataWrapper: ListLiveDataWrapper.Add,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher= Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    fun comeback() {
        clear.clearViewModel(AddViewModel::class.java)
    }

    fun add(value: String) {
        viewModelScope.launch(dispatcher) {
            val id = repository.add(value)
            withContext(dispatcherMain) {
                liveDataWrapper.add(ItemUi(id, value))
            }
            comeback()
        }
    }
}