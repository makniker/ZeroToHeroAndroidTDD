package ru.easycode.zerotoheroandroidtdd.folder.edit

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

data class EditFolderScreen(private val folderId: Long) : Screen {
    override fun show(supportFragmentManager: FragmentManager, container: Int) {
        val bundle = Bundle()
        bundle.putLong("folderId", folderId)
        val fragment = EditFolderFragment()
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "edit_folder")
    }

}
