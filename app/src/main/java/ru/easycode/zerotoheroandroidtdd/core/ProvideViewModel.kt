package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsViewModel
import ru.easycode.zerotoheroandroidtdd.folder.details.NoteListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderViewModel
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderListViewModel
import ru.easycode.zerotoheroandroidtdd.main.MainViewModel
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.core.Now
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteViewModel
import ru.easycode.zerotoheroandroidtdd.note.edit.NoteLiveDataWrapper

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(private val provideViewModel: ProvideViewModel) : ProvideViewModel,
        ClearViewModels {
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

    class Base(
        notesDao: NotesDao,
        foldersDao: FoldersDao,
        private val clear: ClearViewModels
    ) : ProvideViewModel {
        private val navigation = Navigation.Base()
        private val now = Now.Base()
        private val notesRepository = NotesRepository.Base(now, notesDao)
        private val foldersRepository = FoldersRepository.Base(now, foldersDao, notesDao)
        private val folderListLiveDataWrapper = FolderListLiveDataWrapper.Base()
        private val folderLiveDataWrapper = FolderLiveDataWrapper.Base()
        private val noteListLiveDataWrapper = NoteListLiveDataWrapper.Base()
        private val noteLiveDataWrapper = NoteLiveDataWrapper.Base()
        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {
                (MainViewModel::class.java) -> MainViewModel(navigation) as T
                (FolderListViewModel::class.java) -> FolderListViewModel(
                    foldersRepository,
                    folderListLiveDataWrapper,
                    folderLiveDataWrapper,
                    navigation
                ) as T

                (EditFolderViewModel::class.java) -> EditFolderViewModel(
                    folderLiveDataWrapper,
                    foldersRepository,
                    navigation,
                    clear
                ) as T

                (CreateFolderViewModel::class.java) -> CreateFolderViewModel(
                    foldersRepository,
                    folderListLiveDataWrapper,
                    navigation,
                    clear
                ) as T

                (FolderDetailsViewModel::class.java) -> FolderDetailsViewModel(
                    notesRepository,
                    noteListLiveDataWrapper,
                    folderLiveDataWrapper,
                    navigation,
                    clear
                ) as T

                (CreateNoteViewModel::class.java) -> CreateNoteViewModel(
                    folderLiveDataWrapper,
                    noteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear
                ) as T

                (EditNoteViewModel::class.java) -> EditNoteViewModel(
                    folderLiveDataWrapper,
                    noteLiveDataWrapper,
                    noteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear
                ) as T
                else -> {
                    throw Exception()
                }
            }
        }

    }
}
