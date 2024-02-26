package ru.easycode.zerotoheroandroidtdd.presentation.delete

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi
import ru.easycode.zerotoheroandroidtdd.presentation.core.ClearViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.core.ListLiveDataWrapper

class DeleteViewModel(
    private val deleteLiveDataWrapper: ListLiveDataWrapper.Delete,
    private val repository: Repository.Delete,
    private val clear: ClearViewModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    val liveData = MutableLiveData<String>()
    fun init(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            liveData.postValue(repository.item(itemId).text)
        }
    }

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.delete(itemId)
            withContext(dispatcherMain) {
                deleteLiveDataWrapper.delete(ItemUi(itemId, liveData.value!!))
            }
            comeback()
        }
    }

    fun comeback() {
        clear.clearViewModel(DeleteViewModel::class.java)
    }
}