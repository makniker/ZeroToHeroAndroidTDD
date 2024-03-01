package ru.easycode.zerotoheroandroidtdd.folder.create

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.main.Screen

object CreateFolderScreen: Screen {
    override fun show(supportFragmentManager: FragmentManager, container: Int) {
        CreateFolderFragment().show(supportFragmentManager, "create_folder")
    }

}
