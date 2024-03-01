package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

object FoldersListScreen: Screen {
    override fun show(supportFragmentManager: FragmentManager, container: Int) {
        supportFragmentManager.beginTransaction().replace(container, FolderListFragment()).addToBackStack("fragment_list").commit()
    }
}