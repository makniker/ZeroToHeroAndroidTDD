package ru.easycode.zerotoheroandroidtdd.create

import androidx.fragment.app.FragmentManager
import ru.easycode.zerotoheroandroidtdd.core.Screen

object Pop : Screen {
    override fun show(manager: FragmentManager) {
        manager.popBackStack()
    }
}