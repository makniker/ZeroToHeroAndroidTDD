package ru.easycode.zerotoheroandroidtdd.core

interface Screen {
    object Pop : Screen {}

    object CreateScreen : Screen {}

    object ListScreen : Screen {}
}