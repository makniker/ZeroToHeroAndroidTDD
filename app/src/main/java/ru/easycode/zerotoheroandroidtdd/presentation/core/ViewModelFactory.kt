package ru.easycode.zerotoheroandroidtdd.presentation.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.data.ItemsDao
import ru.easycode.zerotoheroandroidtdd.data.Repository
import ru.easycode.zerotoheroandroidtdd.presentation.add.AddViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.delete.DeleteViewModel
import ru.easycode.zerotoheroandroidtdd.presentation.main.MainViewModel

interface ViewModelFactory: ProvideViewModel, ClearViewModel {
    class Base(private val provideViewModel: ProvideViewModel) : ViewModelFactory {
        private val map: MutableMap<Class<out ViewModel>, ViewModel> = mutableMapOf()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            if (!map.containsKey(viewModelClass)) {
                val vm = provideViewModel.viewModel(viewModelClass)
                map[viewModelClass] = vm
            }
            return map[viewModelClass] as T
        }

        override fun clearViewModel(clasz: Class<out ViewModel>) {
            map.remove(clasz)
        }

    }
}

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T
    class Base(
        private val clearViewModel: ClearViewModel, dao: ItemsDao
    ) : ProvideViewModel {
        private val repository = Repository.Base(dao, Now.Base())
        private val liveDataWrapper = ListLiveDataWrapper.Base()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                (AddViewModel::class.java) -> {
                    AddViewModel(repository, liveDataWrapper, clearViewModel) as T
                }
                (MainViewModel::class.java) -> {
                    MainViewModel(repository, liveDataWrapper) as T
                }
                (DeleteViewModel::class.java) -> {
                    DeleteViewModel(liveDataWrapper, repository, clearViewModel) as T
                }
                else -> {
                    throw Exception("not implemented")
                }
            }
        }

    }
}