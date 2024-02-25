package ru.easycode.zerotoheroandroidtdd.create

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.Screen

object CreateScreen : Screen {
    override fun show(manager: FragmentManager) {
        manager.commit {
            replace<CreateFragment>(R.id.fragment_container)
            setReorderingAllowed(true)
            addToBackStack("name1")
        }
    }
}