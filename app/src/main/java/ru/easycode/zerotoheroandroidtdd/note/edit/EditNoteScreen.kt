package ru.easycode.zerotoheroandroidtdd.note.edit

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class EditNoteScreen(private val noteId: Long): Screen {
    override fun show(supportFragmentManager: FragmentManager, container: Int) {
        val bundle = Bundle()
        bundle.putLong("noteId", noteId)
        val fragment = EditNoteFragment()
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "edit_folder")
    }

}
