package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.core.Now

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(private val provideViewModel: ProvideViewModel): ProvideViewModel, ClearViewModels {
        private val map: MutableMap<Class<out ViewModel>, ViewModel> = mutableMapOf()
        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            if (!map.containsKey(clasz)) {
                val vm = provideViewModel.viewModel(clasz)
                map[clasz] = vm
            }
            return map[clasz] as T
        }

        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            for (vm in viewModelClasses) {
                map.remove(vm)
            }
        }
    }

    class Base(notesDao: NotesDao, foldersDao: FoldersDao, clearViewModels: ClearViewModels) : ProvideViewModel {
        private val navigation = Navigation.Base()
        private val now = Now.Base()
        private val notesRepository = NotesRepository.Base(now, )
        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when(clasz) {
                (MainViewModel::class.java) -> MainViewModel(navigation) as T
                (FolderListViewModel::class.java) -> FolderListViewModel() as T
                else -> {
                    throw Exception()
                }
            }
        }

    }
}
