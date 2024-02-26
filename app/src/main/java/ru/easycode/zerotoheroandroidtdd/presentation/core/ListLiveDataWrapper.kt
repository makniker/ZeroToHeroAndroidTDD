package ru.easycode.zerotoheroandroidtdd.presentation.core

import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

interface ListLiveDataWrapper {
    interface Mutable : Update, Read
    class Base : LiveDataWrapper.Abstract<ItemUi>(), All
    interface Read : LiveDataWrapper.Read<ItemUi>
    interface Update : LiveDataWrapper.Update<ItemUi>
    interface All: Mutable, Add, Delete
    interface Add : LiveDataWrapper.Add<ItemUi>
    interface Delete: LiveDataWrapper.Delete<ItemUi>
}