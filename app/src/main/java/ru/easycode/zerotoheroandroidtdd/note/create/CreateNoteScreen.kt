package ru.easycode.zerotoheroandroidtdd.note.create

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class CreateNoteScreen(private val folderId: Long): Screen {
    override fun show(supportFragmentManager: FragmentManager, container: Int) {
        val bundle = Bundle()
        bundle.putLong("folderId", folderId)
        val fragment = CreateNoteFragment()
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "create_note")
    }

}
