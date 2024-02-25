package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.create.CreateViewModel
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel

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

        override fun clear(viewModelClass: Class<out ViewModel>) {
            map.remove(viewModelClass)
        }

    }
}

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T
    class Base(
        private val clearViewModel: ClearViewModel
    ) : ProvideViewModel {
        private val navigation = Navigation.Base()
        private val liveDataWrapper: ListLiveDataWrapper.All = ListLiveDataWrapper.Base()
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
