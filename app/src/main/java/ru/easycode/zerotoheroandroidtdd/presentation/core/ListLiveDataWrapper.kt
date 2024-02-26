package ru.easycode.zerotoheroandroidtdd.presentation.core

import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

interface ListLiveDataWrapper {
    interface Mutable : Update, Read
    class Base : LiveDataWrapper.Abstract<ItemUi>(), All {
        override fun update(value: ItemUi) {
            liveData.value?.let {
                val list = liveData.value!!.toMutableList()
                val v = list.find { it.areItemsSame(value) }
                list[list.indexOf(v)] = value
                update(list)
            }
        }
    }
    interface Change : LiveDataWrapper.Change<ItemUi>
    interface Read : LiveDataWrapper.Read<ItemUi>
    interface Update : LiveDataWrapper.Update<ItemUi>
    interface All: Mutable, Add, Delete, Update, Change
    interface Add : LiveDataWrapper.Add<ItemUi>
    interface Delete: LiveDataWrapper.Delete<ItemUi>
}