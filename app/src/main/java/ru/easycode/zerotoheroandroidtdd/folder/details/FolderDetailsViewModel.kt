package ru.easycode.zerotoheroandroidtdd.folder.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.easycode.zerotoheroandroidtdd.core.ClearViewModels
import ru.easycode.zerotoheroandroidtdd.core.LiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.edit.EditFolderScreen
import ru.easycode.zerotoheroandroidtdd.folder.list.FolderUi
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation
import ru.easycode.zerotoheroandroidtdd.note.core.NotesRepository
import ru.easycode.zerotoheroandroidtdd.note.create.CreateNoteScreen
import ru.easycode.zerotoheroandroidtdd.note.edit.EditNoteScreen

class FolderDetailsViewModel(
    private val noteListRepository: NotesRepository.ReadList,
    private val liveDataWrapper: NoteListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Mutable,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), LiveDataWrapper.Read<FolderUi> {
    fun init() {
        viewModelScope.launch(dispatcher) {
            val id = folderLiveDataWrapper.folderId()
            val list = noteListRepository.noteList(id)
            withContext(dispatcherMain) {
                liveDataWrapper.update(list.map { NoteUi(it.id, it.title, it.folderId) })
            }
        }
    }

    fun createNote() {
        val id = folderLiveDataWrapper.folderId()
        navigation.update(CreateNoteScreen(id))
    }

    fun comeback() {
        clear.clear(FolderDetailsViewModel::class.java)
        navigation.update(FoldersListScreen)
    }

    fun editNote(note: NoteUi) {
        folderLiveDataWrapper.folderId()
        navigation.update(EditNoteScreen(note.id))
    }

    fun editFolder() {
        val id = folderLiveDataWrapper.folderId()
        navigation.update(EditFolderScreen(id))
    }

    override fun liveData(): LiveData<FolderUi> = folderLiveDataWrapper.liveData()
    fun notesLiveData(): LiveData<List<NoteUi>> = liveDataWrapper.liveData()
}
