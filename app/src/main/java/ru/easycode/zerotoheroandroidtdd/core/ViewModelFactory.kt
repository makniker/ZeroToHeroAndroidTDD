package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel

interface ViewModelFactory {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T
    fun <T : ViewModel> clear(viewModelClass: Class<T>)
    class Base(private val provideViewModel: ProvideViewModel) : ViewModelFactory {
        private val list: MutableList<ViewModel> = mutableListOf()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            if (list.filterIsInstance(viewModelClass).isEmpty()) {
                val vm = provideViewModel.viewModel(viewModelClass)
                list.add(vm)
            }
            return list.filterIsInstance(viewModelClass)[0]
        }

        override fun <T : ViewModel> clear(viewModelClass: Class<T>) {
            list.clear()
        }

    }
}

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T
    class Base(
        private val navigation: Navigation.Mutable,
        private val liveDataWrapper: ListLiveDataWrapper.All,
        private val clearViewModel: ClearViewModel
    ) : ProvideViewModel {
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                (CreateViewModel::class.java) -> {
                    val ld: ListLiveDataWrapper.Add = liveDataWrapper
                    val nav: Navigation.Update = navigation
                    CreateViewModel(ld, nav, clearViewModel) as T
                }

                (ListViewModel::class.java) -> {
                    val nav: Navigation.Update = navigation
                    val ld: ListLiveDataWrapper.Mutable = liveDataWrapper
                    ListViewModel(ld, nav) as T
                }

                (MainViewModel::class.java) -> {
                    MainViewModel(navigation) as T
                }
                else -> {
                    throw Exception("not implemented")
                }
            }
        }

    }
}
